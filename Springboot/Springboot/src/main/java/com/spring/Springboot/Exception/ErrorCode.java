package com.spring.Springboot.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(1004, "Password Invalid", HttpStatus.BAD_REQUEST),
  USER_NOT_EXIST(1005, "USER NOT EXIST", HttpStatus.NOT_FOUND),
  UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  INVALID_KEY(1001, "INVALID MESSAGE KEY", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(1007, "Do not have permission", HttpStatus.FORBIDDEN),
  INVALIDDOB(1008, "You must be at least {min}", HttpStatus.BAD_REQUEST);
  private int code;
  private String message;

  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.statusCode = statusCode;
    this.code = code;
    this.message = message;
  }

  private HttpStatusCode statusCode;
}
