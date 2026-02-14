package com.spring.Springboot.Controller;

import com.spring.Springboot.Service.RoleService;
import com.spring.Springboot.dto.Request.ApiResponse;
import com.spring.Springboot.dto.Request.RoleRequest;
import com.spring.Springboot.dto.Response.RoleResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

  RoleService roleService;

  @PostMapping
  ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
    return ApiResponse.<RoleResponse>builder().result(roleService.create(request)).build();
  }

  @GetMapping
  ApiResponse<List<RoleResponse>> getAll() {
    return ApiResponse.<List<RoleResponse>>builder().result(roleService.getAll()).build();
  }

  @DeleteMapping("/{roles}")
  ApiResponse<Void> delete(@PathVariable String roles) {
    roleService.delete(roles);
    return ApiResponse.<Void>builder().build();
  }
}
