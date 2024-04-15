package com.vp.fittrack.controllers;

import com.vp.fittrack.services.ExerciseService;
import com.vp.fittrack.services.TrainingService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.services.UserDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WorkoutZoneController {

  private final UserDataService userDataService;

  private final TrainingService trainingService;

  private final ExerciseService exerciseService;

  @Autowired
  public WorkoutZoneController(UserDataService userDataService, TrainingService trainingService,
      ExerciseService exerciseService) {
    this.userDataService = userDataService;
    this.trainingService = trainingService;
    this.exerciseService = exerciseService;
  }

  @GetMapping("/workout/zone/{id}")
  public String workoutZone(@PathVariable Long id, Model model, HttpSession session) {
    UserData user = userDataService.findUserById(id);
    List<Exercise> exercises = user.getExercises().stream()
        .filter(exercise -> !exercise.isDeleted())
        .collect(Collectors.toList());
    System.out.println(user.getExercises().size());
    List<Training> trainings = user.getTrainings();
    Collections.sort(trainings, Comparator.comparing(Training::getTimeOfCreation).reversed());
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    model.addAttribute("exercises", exercises);
    model.addAttribute("trainings", trainings);
    System.out.println(LocalDateTime.now());
     return "workoutZone";
    }

  @GetMapping("/interval/practice/{id}")
  public String startPractice(@PathVariable Long id, Model model) {
    UserData user = userDataService.findUserById(id);
    model.addAttribute("id", user.getId());
    return "intervalPractice";
  }

  @GetMapping("/{userId}/training/{trainingId}")
  public String startTraining(@PathVariable Long userId, @PathVariable Long trainingId, Model model) {
    UserData user = userDataService.findUserById(userId);
    Training training = trainingService.findTrainingById(trainingId);
    for (Exercise exercise : training.getExercises()) {
      System.out.println(exercise.getName());
    }
    List<Exercise> exercises = exerciseService.getExercisesForPractice(trainingId);
    for (Exercise exercise : exercises) {
      System.out.println(exercise);
    }
    model.addAttribute("exercises", exercises);
    model.addAttribute("rounds", training.getRounds());
    model.addAttribute("userId", user.getId());
//    model.addAttribute("restBetweenExercises", 5);
//    model.addAttribute("trainingName", training.getName());
//    model.addAttribute("seriesName", "test");
    return "practice";
  }
}
