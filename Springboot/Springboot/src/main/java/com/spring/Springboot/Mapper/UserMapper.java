package com.spring.Springboot.Mapper;

import com.spring.Springboot.Entity.User;
import com.spring.Springboot.dto.Request.UserCreationRequest;
import com.spring.Springboot.dto.Request.UserUpdateRequest;
import com.spring.Springboot.dto.Response.UserResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "roles", ignore = true)
  User toUser(UserCreationRequest request);

  @Mapping(target = "roles", ignore = true)
  void updateUser(@MappingTarget User user, UserUpdateRequest request);

  UserResponse toUserResponse(User user);

  List<UserResponse> toListUserResponse(List<User> users);
}
