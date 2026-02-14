package com.spring.Springboot.Exception;

import com.spring.Springboot.dto.Request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String Min_Attribute = "min";

  @ExceptionHandler(value = RuntimeException.class)
  ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(9999);
    apiResponse.setMessage(exception.getMessage());
    return ResponseEntity.badRequest().body(apiResponse);
  }

  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(
            ApiResponse.builder().code(errorCode.getCode()).result(errorCode.getMessage()).build());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {

    String enumkey = exception.getFieldError().getDefaultMessage();
    ErrorCode errorCode = ErrorCode.INVALID_KEY;
    Map<String, Object> attributes = null;
    try {
      errorCode = ErrorCode.valueOf(enumkey);
      var constraintviolation =
          exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
      attributes = constraintviolation.getConstraintDescriptor().getAttributes();
      log.info(attributes.toString());
    } catch (IllegalArgumentException e) {
    }

    ApiResponse apiresponse = new ApiResponse();
    apiresponse.setCode(errorCode.getCode());
    apiresponse.setMessage(
        Objects.nonNull(attributes)
            ? mapAttribute(errorCode.getMessage(), attributes)
            : errorCode.getMessage());

    return ResponseEntity.badRequest().body(apiresponse);
  }

  private String mapAttribute(String message, Map<String, Object> attribute) {
    String minValue = String.valueOf(attribute.get(Min_Attribute));

    return message.replace("{" + Min_Attribute + "}", minValue);
  }
}
