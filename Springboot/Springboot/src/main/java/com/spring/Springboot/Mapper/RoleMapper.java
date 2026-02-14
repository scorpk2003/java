package com.spring.Springboot.Mapper;

import com.spring.Springboot.Entity.Role;
import com.spring.Springboot.dto.Request.RoleRequest;
import com.spring.Springboot.dto.Response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest request);

  RoleResponse toRoleResponse(Role role);
}
