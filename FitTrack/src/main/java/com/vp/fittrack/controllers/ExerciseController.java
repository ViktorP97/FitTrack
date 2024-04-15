//package com.vp.fittrack.controllers;
//
//import org.springframework.ui.Model;
//import com.vp.fittrack.dtos.ExerciseDto;
//import com.vp.fittrack.models.UserData;
//import com.vp.fittrack.services.ExerciseService;
//import com.vp.fittrack.services.UserDataService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class ExerciseController {
//
//  private final ExerciseService exerciseService;
//
//  @Autowired
//  public ExerciseController(ExerciseService exerciseService, UserDataService userDataService) {
//    this.exerciseService = exerciseService;
//  }
//
//  @GetMapping("/add/exercise/{id}")
//  public String addExercise(@PathVariable Long id, Model model) {
//    model.addAttribute("id", id);
//    return "addExercise";
//  }
//
////  @PostMapping("/save/exercise/{id}")
////  public String saveExercise(@PathVariable Long id, ExerciseDto exerciseDto) {
////    exerciseService.saveExercise(exerciseDto, id);
////    return "redirect:/workout/zone/" + id;
////  }
//
////  @PostMapping("/{userId}/delete/exercise/{exerciseId}")
////  public String deleteExercise(@PathVariable Long userId, @PathVariable Long exerciseId) {
////    exerciseService.removeExercise(userId, exerciseId);
////    return "redirect:/workout/zone/" + userId;
////  }
//}
