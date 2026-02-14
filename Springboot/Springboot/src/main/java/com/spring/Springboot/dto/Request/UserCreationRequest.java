package com.spring.Springboot.dto.Request;

import com.spring.Springboot.Validator.DobConstraint;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
  @Size(min = 3, message = "USERNAME_INVALID")
  String username;

  @Size(min = 8, message = "INVALID_PASSWORD")
  String password;

  String firstname;
  String lastname;

  @DobConstraint(min = 16, message = "INVALIDDOB")
  LocalDate dob;

  List<String> roles;
}
