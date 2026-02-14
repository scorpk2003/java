package com.spring.Springboot.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.Springboot.Service.UserService;
import com.spring.Springboot.dto.Request.UserCreationRequest;
import com.spring.Springboot.dto.Response.UserResponse;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  private UserCreationRequest request;
  private UserResponse response;
  private LocalDate dob;

  @BeforeEach
  void initData() {
    dob = LocalDate.of(2003, 1, 11);

    request =
        UserCreationRequest.builder()
            .username("Khang")
            .firstname("Cao")
            .lastname("Khang")
            .password("12345678")
            .dob(dob)
            .build();

    response =
        UserResponse.builder()
            .id("cd9324cdc94534")
            .username("Khang")
            .firstname("Cao")
            .lastname("Khang")
            .dob(dob)
            .build();
  }

  @Test
  void createUser_validRequest_success() throws Exception {
    // Given
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String content = objectMapper.writeValueAsString(request);

    Mockito.when(userService.createRequest(ArgumentMatchers.any())).thenReturn(response);

    // When, Then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
  }

  @Test
  void createUser_usernameInvalid_fail() throws Exception {
    // Given
    request.setUsername("11");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String content = objectMapper.writeValueAsString(request);

    // When, Then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
        .andExpect(
            MockMvcResultMatchers.jsonPath("message")
                .value("Username must be at least 3 characters"));
  }
}
