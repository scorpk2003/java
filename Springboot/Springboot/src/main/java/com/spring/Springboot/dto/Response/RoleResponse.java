package com.spring.Springboot.dto.Response;

import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
  String name;
  String description;

  Set<PermissionResponse> permissions;
}
