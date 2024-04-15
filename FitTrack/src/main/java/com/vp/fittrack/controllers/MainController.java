package com.vp.fittrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/home")
  public String home() {
    return "mainPage";
  }
}
