package com.vp.fittrack.restControllers;

import com.vp.fittrack.dtos.TrainingDto;
import com.vp.fittrack.dtos.TrainingResponse;
import com.vp.fittrack.exceptions.TrainingNotFoundException;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.TrainingRepository;
import com.vp.fittrack.services.ExerciseService;
import com.vp.fittrack.services.TrainingService;
import com.vp.fittrack.services.UserDataService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingRestController {

  private final ExerciseService exerciseService;

  private final TrainingService trainingService;

  private final TrainingRepository trainingRepository;

  private final UserDataService userDataService;

  @Autowired
  public TrainingRestController(ExerciseService exerciseService, TrainingService trainingService,
      TrainingRepository trainingRepository, UserDataService userDataService) {
    this.exerciseService = exerciseService;
    this.trainingService = trainingService;
    this.trainingRepository = trainingRepository;
    this.userDataService = userDataService;
  }

  @PostMapping("/save/training/{id}")
  public ResponseEntity<TrainingResponse> saveTraining(@PathVariable Long id, TrainingDto trainingDto) {
    Training training = trainingService.saveTraining(id, trainingDto);
    List<Exercise> exerciseList = training.getExercises();
    String exercises = exerciseList.stream()
        .map(Exercise::getName)
        .collect(Collectors.joining(", "));

    String exercisesTypes = exerciseList.stream()
        .map(Exercise::getType)
        .collect(Collectors.joining(", "));

    TrainingResponse response = new TrainingResponse(training, exercises, exercisesTypes);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  @PostMapping("/{userId}/delete/training/{trainingId}")
  public ResponseEntity<List<Training>> deleteTraining(@PathVariable Long userId, @PathVariable Long trainingId) {
    try {
      trainingService.removeTraining(userId, trainingId);

      UserData user = userDataService.findUserById(userId);

      List<Training> trainings = user.getTrainings();
      return ResponseEntity.ok().body(trainings);
    } catch (TrainingNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{userId}/find/types/{trainingId}")
  public ResponseEntity<Map<String, String>> findTypesOfTraininigs(@PathVariable Long userId, @PathVariable Long trainingId) {
    try {
      String exercisesTypes = trainingService.getExercisesTypes(userId, trainingId);
      Map<String, String> response = new HashMap<>();
      response.put("types", exercisesTypes);
      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (TrainingNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
