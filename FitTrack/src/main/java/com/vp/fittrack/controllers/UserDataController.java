package com.vp.fittrack.controllers;

import com.vp.fittrack.exceptions.UserNotFoundException;
import com.vp.fittrack.dtos.LoginDto;
import com.vp.fittrack.dtos.RegisterDto;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.services.UserDataService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserDataController {

  private final UserDataService userService;

  @Autowired
  public UserDataController(UserDataService userService) {
    this.userService = userService;
  }

  @GetMapping("/register")
  public String showRegistrationForm() {
    return "registerUser";
  }

  @PostMapping("/register")
  public String registerUser(RegisterDto registerDto, Model model) {
    if (StringUtils.isBlank(registerDto.getName())) {
      model.addAttribute("wrongInput", true);
      model.addAttribute("missingInput", true);
      return "registerUser";
    }
    if (StringUtils.isBlank(registerDto.getPassword())) {
      model.addAttribute("wrongInput", true);
      model.addAttribute("missingInput", true);
      return "registerUser";
    }
    if (userService.nameExist(registerDto.getName())) {
      model.addAttribute("wrongInput", true);
      model.addAttribute("nameExist", true);
      return "registerUser";
    }
    if (userService.emailExist(registerDto.getEmail())) {
      model.addAttribute("wrongInput", true);
      model.addAttribute("mailExist", true);
      return "registerUser";
    }
    UserData user = userService.register(registerDto);
    System.out.println("User registered: " + registerDto.getName());
    return "redirect:/register/success/" + user.getToken();
  }

  @GetMapping("/verify/{mail}")
  public String verifyUser(@PathVariable String mail) {
    userService.verifyUser(mail);
    return "verifyUser";
  }

  @GetMapping("/register/success/{token}")
  public String verifyMessage(@PathVariable String token, Model model) {
    UserData user = userService.findUserByToken(token);
    if (user != null) {
      model.addAttribute("mail", user.getEmail());
      System.out.println("/verify/" + user.getEmail());
    }
    return "verificationMessage";
  }

  @GetMapping("/login")
  public String loginUser() {
    return "loginUser";
  }

  @PostMapping("/login")
  public String mainPage(@ModelAttribute LoginDto loginDto, Model model) {
    try {
      if (!userService.userRegistered(loginDto.getName())) {
        model.addAttribute("wrongInput", true);
        model.addAttribute("wrongName", loginDto.getName());
        return "loginUser";
      }
      if (userService.wrongPassword(loginDto.getPassword(), loginDto.getName())) {
        model.addAttribute("wrongInput", true);
        model.addAttribute("wrongPassword", true);
        return "loginUser";
      }

      try {
        if (userService.notVerified(loginDto.getName())) {
          model.addAttribute("wrongInput", true);
          model.addAttribute("notVerified", true);
          return "loginUser";
        }
      } catch (UserNotFoundException e) {
        model.addAttribute("wrongInput", true);
        model.addAttribute("wrongName", loginDto.getName());
        return "loginUser";
      }

      UserData user = userService.logInUser(loginDto.getName());
      if (user == null) {
        model.addAttribute("wrongInput", true);
        model.addAttribute("wrongName", loginDto.getName());
        return "loginUser";
      }
      return "redirect:/workout/zone/" + user.getId();
    } catch (UserNotFoundException e) {
      model.addAttribute("wrongInput", true);
      model.addAttribute("wrongName", loginDto.getName());
      return "loginUser";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    return "redirect:/login";
  }
}
