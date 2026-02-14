package com.spring.Springboot.Mapper;

import com.spring.Springboot.Entity.Permission;
import com.spring.Springboot.dto.Request.PermissionRequest;
import com.spring.Springboot.dto.Response.PermissionResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  Permission toPermission(PermissionRequest request);

  void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);

  List<PermissionResponse> toListPermissionResponse(List<Permission> permissions);
}
