package com.vp.fittrack.restControllers;

import com.vp.fittrack.exceptions.ExerciseNameExistException;
import com.vp.fittrack.dtos.ExerciseDto;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.services.ExerciseService;
import com.vp.fittrack.services.TrainingService;
import com.vp.fittrack.services.UserDataService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseRestController {

  private final TrainingService trainingService;

  private final ExerciseService exerciseService;

  private final UserDataService userDataService;
  public ExerciseRestController(TrainingService trainingService, ExerciseService exerciseService,
      UserDataService userDataService) {
    this.trainingService = trainingService;
    this.exerciseService = exerciseService;
    this.userDataService = userDataService;
  }


  @GetMapping("/getExerciseData/{id}")
  public ResponseEntity<Map<String, Object>> getExerciseData(@PathVariable Long id) {
    Map<String, Object> responseData = new HashMap<>();

    Training training = trainingService.findTrainingById(id);
    List<Exercise> exercises = exerciseService.getExercisesForPractice(id);

    responseData.put("exercises", exercises);
    responseData.put("restDuration", 5);

    return ResponseEntity.ok(responseData);
  }

  @PostMapping("/{userId}/delete/exercise/{exerciseId}")
  public ResponseEntity<List<Exercise>> deleteExercise(@PathVariable Long userId, @PathVariable Long exerciseId) {
    // Your logic to remove the exercise
    exerciseService.removeExercise(userId, exerciseId);

    UserData user = userDataService.findUserById(userId);

    List<Exercise> exercises = user.getExercises().stream()
        .filter(exercise -> !exercise.isDeleted())
        .collect(Collectors.toList());
    // Set the Location header to the new URL
    String redirectUrl = "/workout/zone/" + userId;

    // Use ResponseEntity to set headers and status code
//    return ResponseEntity.status(HttpStatus.SEE_OTHER)
//        .location(URI.create(redirectUrl))
//        .build();
    return ResponseEntity.ok().body(exercises);
  }

  @PostMapping("/save/exercise/{id}")
  public ResponseEntity<?> saveExercise(@PathVariable Long id, ExerciseDto exerciseDto) {
    try {
      Exercise exercise = exerciseService.saveExercise(exerciseDto, id);
      return ResponseEntity.ok().body(exercise);
    } catch (ExerciseNameExistException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }
  }
}
