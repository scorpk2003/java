package com.spring.Springboot.Service;

import com.spring.Springboot.Entity.User;
import com.spring.Springboot.Exception.AppException;
import com.spring.Springboot.Exception.ErrorCode;
import com.spring.Springboot.Mapper.UserMapper;
import com.spring.Springboot.Repository.RoleRepository;
import com.spring.Springboot.Repository.UserRepository;
import com.spring.Springboot.dto.Request.UserCreationRequest;
import com.spring.Springboot.dto.Request.UserUpdateRequest;
import com.spring.Springboot.dto.Response.UserResponse;
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

  UserRepository userRepo;

  RoleRepository roleRepository;

  UserMapper userMapper;

  PasswordEncoder passwordEncoder;

  public UserResponse createRequest(UserCreationRequest request) {

    if (userRepo.existsByUsername(request.getUsername()))
      throw new AppException(ErrorCode.USER_EXISTED);

    User user = userMapper.toUser(request);

    user.setPassword(passwordEncoder.encode(request.getPassword()));

    var roles = request.getRoles();
    user.setRoles(new HashSet<>(roleRepository.findAllById(roles)));

    return userMapper.toUserResponse(userRepo.save(user));
  }

//  @PreAuthorize("hasRole('ADMIN')")
  public List<UserResponse> getUsers() {

    log.info("In method get Users");
    return userMapper.toListUserResponse(userRepo.findAll());
  }

  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getUser(String id) {
    return userMapper.toUserResponse(
        userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST)));
  }

  public UserResponse updateUser(String userid, UserUpdateRequest request) {
    User user =
        userRepo.findById(userid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

    userMapper.updateUser(user, request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    var roles = roleRepository.findAllById(request.getRoles());
    user.setRoles(new HashSet<>(roles));

    return userMapper.toUserResponse(userRepo.save(user));
  }

  public void delUser(String userID) {
    userRepo.deleteById(userID);
  }

  public UserResponse getMyInfo() {
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();
    User user =
        userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
    return userMapper.toUserResponse(user);
  }
}
