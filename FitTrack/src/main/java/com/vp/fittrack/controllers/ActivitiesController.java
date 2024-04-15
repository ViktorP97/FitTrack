package com.vp.fittrack.controllers;

import com.vp.fittrack.models.UserData;
import com.vp.fittrack.services.UserDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ActivitiesController {

  private final UserDataService userDataService;

  public ActivitiesController(UserDataService userDataService) {
    this.userDataService = userDataService;
  }


  @GetMapping("/activities/{id}")
  public String workoutZone(@PathVariable Long id, Model model) {
    UserData user = userDataService.findUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "activtiesSpot";
  }
}
