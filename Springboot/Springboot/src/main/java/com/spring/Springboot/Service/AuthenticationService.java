package com.spring.Springboot.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.spring.Springboot.Entity.InvalidatedToken;
import com.spring.Springboot.Entity.User;
import com.spring.Springboot.Exception.AppException;
import com.spring.Springboot.Exception.ErrorCode;
import com.spring.Springboot.Repository.InvalidatedRepository;
import com.spring.Springboot.Repository.UserRepository;
import com.spring.Springboot.dto.Request.AuthenticationRequest;
import com.spring.Springboot.dto.Request.IntrospectRequest;
import com.spring.Springboot.dto.Request.LogoutRequest;
import com.spring.Springboot.dto.Request.RefreshRequest;
import com.spring.Springboot.dto.Response.AuthenticationResponse;
import com.spring.Springboot.dto.Response.IntrospectResponse;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

  UserRepository userRepository;

  InvalidatedRepository invalidatedRepository;

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;

  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;

  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURATION;

  public IntrospectResponse introspect(IntrospectRequest request)
      throws ParseException, JOSEException {
    var token = request.getToken();

    boolean isvalid = true;

    try {
      verifyToken(token, false);
    } catch (AppException e) {
      isvalid = false;
    }
    return IntrospectResponse.builder().valid(isvalid).build();
  }

  public AuthenticationResponse refreshTolen(RefreshRequest request)
      throws ParseException, JOSEException {
    var signJWT = verifyToken(request.getToken(), true);

    var jit = signJWT.getJWTClaimsSet().getJWTID();
    var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

    InvalidatedToken invalidatedToken =
        InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

    invalidatedRepository.save(invalidatedToken);

    var username = signJWT.getJWTClaimsSet().getSubject();

    var user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

    var token = genarateToken(user);

    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    var user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
    if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

    var token = genarateToken(user);

    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }

  public void logout(LogoutRequest request) throws ParseException, JOSEException {
    try {

      var signToken = verifyToken(request.getToken(), true);

      String jit = signToken.getJWTClaimsSet().getJWTID();
      Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

      InvalidatedToken invalidatedToken =
          InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

      invalidatedRepository.save(invalidatedToken);
    } catch (AppException exception) {
      log.info("Token is already expired");
    }
  }

  private SignedJWT verifyToken(String token, boolean isRefresh)
      throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expryTime =
        (isRefresh)
            ? new Date(
                signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli())
            : signedJWT.getJWTClaimsSet().getExpirationTime();

    var verified = signedJWT.verify(verifier);

    if (!verified && expryTime.after(new Date())) throw new AppException(ErrorCode.UNAUTHENTICATED);

    if (invalidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
      throw new AppException(ErrorCode.UNAUTHENTICATED);

    return signedJWT;
  }

  private String genarateToken(User user) {

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet =
        new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issuer("khangweb.com")
            .issueTime(new Date())
            .expirationTime(
                new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", buildScope(user))
            .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildScope(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(user.getRoles()))
      user.getRoles()
          .forEach(
              role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                  role.getPermissions()
                      .forEach(permission -> stringJoiner.add(permission.getName()));
              });

    return stringJoiner.toString();
  }
}
