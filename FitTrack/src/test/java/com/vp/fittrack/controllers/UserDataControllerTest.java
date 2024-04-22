package com.vp.fittrack.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.vp.fittrack.dtos.LoginDto;
import com.vp.fittrack.services.UserDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(UserDataController.class)
@AutoConfigureMockMvc
public class UserDataControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserDataService userService;

  @Test
  public void UserRegistered() throws Exception {
    when(userService.userRegistered("existingUser")).thenReturn(true);
    when(userService.wrongPassword("correctPassword", "existingUser")).thenReturn(false);
    when(userService.notVerified("existingUser")).thenReturn(false);

    LoginDto loginDto = new LoginDto();
    loginDto.setName("existingUser");
    loginDto.setPassword("correctPassword");

    MockHttpServletRequestBuilder requestBuilder = post("/login")
        .flashAttr("loginDto", loginDto);
    mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(view().name("loginUser"));
  }

  @Test
  public void testUserNotRegistered() throws Exception {
    String invalidUsername = "nonexistentUser";
    mockMvc.perform(post("/login")
            .param("name", invalidUsername)
            .param("password", "somePassword"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("wrongInput"))
        .andExpect(model().attributeExists("wrongName"))
        .andExpect(view().name("loginUser"));
  }

  @Test
  void testWrongPassword() throws Exception {
    LoginDto loginDto = new LoginDto();
    loginDto.setName("existingUser");
    loginDto.setPassword("wrongPassword");

    when(userService.userRegistered(loginDto.getName())).thenReturn(true);
    when(userService.wrongPassword(loginDto.getPassword(), loginDto.getName())).thenReturn(true);

    mockMvc.perform(post("/login")
            .flashAttr("loginDto", loginDto))
        .andExpect(status().isOk())
        .andExpect(model().attribute("wrongInput", true))
        .andExpect(model().attribute("wrongPassword", true))
        .andExpect(view().name("loginUser"))
        .andReturn();
  }
}

