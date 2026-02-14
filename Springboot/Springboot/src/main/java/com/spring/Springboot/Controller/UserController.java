package com.spring.Springboot.Controller;

import com.spring.Springboot.Service.UserService;
import com.spring.Springboot.dto.Request.ApiResponse;
import com.spring.Springboot.dto.Request.UserCreationRequest;
import com.spring.Springboot.dto.Request.UserUpdateRequest;
import com.spring.Springboot.dto.Response.UserResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @PostMapping
  ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
    ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
    apiResponse.setResult(userService.createRequest(request));
    return apiResponse;
  }

  @GetMapping
  ApiResponse<List<UserResponse>> getUsers() {

    var authentication = SecurityContextHolder.getContext().getAuthentication();

    log.info("Username: {}", authentication.getName());
    authentication
        .getAuthorities()
        .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

    return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
  }

  @GetMapping("/myInfo")
  ApiResponse<UserResponse> getMyInfo() {
    return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
  }

  @GetMapping("/{userID}")
  UserResponse getUser(@PathVariable String userID) {
    return userService.getUser(userID);
  }

  @PutMapping("/{userID}")
  UserResponse updateUser(
      @RequestBody UserUpdateRequest userUpdateRequest, @PathVariable String userID) {
    return userService.updateUser(userID, userUpdateRequest);
  }

  @DeleteMapping("/{userID}")
  String delUser(@PathVariable String userID) {
    userService.delUser(userID);
    return "User have been deleted";
  }
}
