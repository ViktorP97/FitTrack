//package com.vp.fittrack.controllers;
//
//import com.vp.fittrack.models.Exercise;
//import com.vp.fittrack.models.Training;
//import com.vp.fittrack.models.UserData;
//import com.vp.fittrack.repositories.TrainingRepository;
//import com.vp.fittrack.services.ExerciseService;
//import com.vp.fittrack.services.TrainingService;
//import com.vp.fittrack.services.UserDataService;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class TrainingController {
//
//  private final ExerciseService exerciseService;
//
//  private final TrainingService trainingService;
//
//  private final TrainingRepository trainingRepository;
//
//  private final UserDataService userDataService;
//
//
//  @Autowired
//  public TrainingController(ExerciseService exerciseService, TrainingService trainingService,
//      TrainingRepository trainingRepository, UserDataService userDataService) {
//    this.exerciseService = exerciseService;
//    this.trainingService = trainingService;
//    this.trainingRepository = trainingRepository;
//    this.userDataService = userDataService;
//  }
//
//  @GetMapping("/training/name/{id}")
//  public String chooseTrainingName(@PathVariable Long id) {
//    UserData user = userDataService.findUserById(id);
//    return "addTraining";
//  }
//
//  @GetMapping("/choose/exercises/{trainingName}")
//  public String chooseExercises(@PathVariable String trainingName, Model model) {
//    List<Exercise> exercises = exerciseService.getAllExercises();
//    model.addAttribute("trainingName", trainingName);
//    model.addAttribute("allExercises", exercises);
//    return "chooseExercises";
//  }
//
////  @PostMapping("/save/training/{trainingName}")
////  public String saveTraining(@PathVariable String trainingName, @RequestParam("exerciseOrder") String exerciseOrder) {
////    Training training = trainingService.saveTraining(trainingName, exerciseOrder);
////    return "redirect:/training/" + training.getId() + "/exercises/time";
////  }
//
//  @GetMapping("/training/{id}/exercises/time")
//  public String addTimeToExercises(@PathVariable Long id, Model model) {
//    Optional<Training> setOptional = trainingRepository.findById(id);
//    if (setOptional.isPresent()) {
//      Training training = setOptional.get();
//      List<Exercise> exercisesInOrder = exerciseService.putExercisesToTheOrder(training.getExerciseOrder(), training.getExercises());
//      model.addAttribute("id", training.getId());
//      model.addAttribute("allExercises", exercisesInOrder);
//    } else {
//      System.out.println("error");
//      return "redirect:/home";
//    }
//    return "addTimeToExercises";
//  }
//
//  @PostMapping("/training/{id}/exercises/time")
//  public String saveTimeOnExercises(@PathVariable Long id,
//      @RequestParam("values") String values) {
//    exerciseService.addTimeToExercises(id, values);
//    String name = "";
//    return "redirect:/training/" + id + "/rounds";
//  }
//
//  @GetMapping("/training/{id}/rounds")
//  public String chooseRounds(@PathVariable Long id, Model model) {
//    model.addAttribute("id", id);
//    return "addRoundsToTraining";
//  }
//
//  @PostMapping("/training/{id}/rounds")
//  public String saveRoundsToTraining(@PathVariable Long id, @RequestParam int rounds) {
//    trainingService.saveRoundsToTraining(id, rounds);
//    return "redirect:/home";
//  }
//
////  @GetMapping("/practice/{id}")
////  public String startPractice(@PathVariable Long id, Model model) {
////    Training training = trainingService.findTrainingById(id);
////    for (Exercise exercise : training.getExercises()) {
////      System.out.println(exercise.getName());
////    }
////    List<Exercise> exercises = exerciseService.getExercisesForPractice(id);
////    for (Exercise exercise : exercises) {
////      System.out.println(exercise);
////    }
////    model.addAttribute("exercises", exercises);
////    model.addAttribute("rounds", training.getRounds());
//////    model.addAttribute("restBetweenExercises", 5);
//////    model.addAttribute("trainingName", training.getName());
//////    model.addAttribute("seriesName", "test");
////    return "fungujeTo";
////  }
//}
