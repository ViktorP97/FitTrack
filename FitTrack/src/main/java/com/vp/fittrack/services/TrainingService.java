package com.vp.fittrack.services;

import com.vp.fittrack.Exceptions.TrainingNameExistException;
import com.vp.fittrack.dtos.RegisterDto;
import com.vp.fittrack.dtos.TrainingDto;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.ExerciseRepository;
import com.vp.fittrack.repositories.TrainingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingService {

  private final TrainingRepository trainingRepository;
  private final ExerciseRepository exerciseRepository;

  private final ExerciseService exerciseService;

  private final UserDataService userDataService;

  @Autowired
  public TrainingService(TrainingRepository trainingRepository, ExerciseRepository exerciseRepository,
      ExerciseService exerciseService, UserDataService userDataService) {
    this.trainingRepository = trainingRepository;
    this.exerciseRepository = exerciseRepository;
    this.exerciseService = exerciseService;
    this.userDataService = userDataService;
  }

  public Training findTrainingById(Long id) {
    Optional<Training> trainingOptional = trainingRepository.findById(id);
    if (trainingOptional.isPresent()) {
      return trainingOptional.get();
    } else {
      System.out.println("error");
      return null;
    }
  }

  public Training saveTraining(String name, String exerciseOrder) {
    List<Exercise> exercises = exerciseService.findExercisesById(exerciseOrder);
    Training training = new Training();
    training.setName(name);
    training.setExerciseOrder(exerciseOrder);

    for (Exercise exercise : exercises) {
      exercise.getTrainings().add(training);
    }
    training.setExercises(exercises);
    trainingRepository.save(training);
    exerciseRepository.saveAll(exercises);
    return training;
  }

  public void saveRoundsToTraining(Long id, int rounds) {
    Optional<Training> setOptional = trainingRepository.findById(id);
    if (setOptional.isPresent()) {
      Training training = setOptional.get();
      training.setRounds(rounds);
      trainingRepository.save(training);
    } else {
      System.out.println("error");
    }
  }

  public boolean trainingNameExist(String trainingName, Long userId) {
    String editedTrainingName = trainingName.toLowerCase();
    UserData user = userDataService.findUserById(userId);
    List<Training> trainings = user.getTrainings();
    if (trainings != null && trainings.size() > 0) {
      for (Training training : trainings) {
        if(training.getName().toLowerCase().equals(editedTrainingName)) {
          return true;
        }
      }
    }
    return false;
  }
  public Training saveTraining2(Long id, TrainingDto trainingDto) {
    UserData user = userDataService.findUserById(id);
    Training training = new Training();
    training.setName(trainingDto.getName());
    training.setExerciseOrder(trainingDto.getExerciseOrder());
    training.setExerciseValues(trainingDto.getExerciseValues());
    training.setRounds(trainingDto.getRounds());
    training.setUser(user);
    training.setTimeOfCreation(LocalDateTime.now());
    List<Exercise> exercises = exerciseService.findExercisesById(trainingDto.getExerciseOrder());
    for (Exercise exercise : exercises) {
      exercise.getTrainings().add(training);
    }
    training.setExercises(exercises);
    trainingRepository.save(training);
    exerciseRepository.saveAll(exercises);
    user.getTrainings().add(training);
    userDataService.saveUser(user);
    return training;
  }

  @Transactional
  public void removeTraining(Long userId, Long trainingId) {
    UserData userData = userDataService.findUserById(userId);
    List<Training> trainings = userData.getTrainings();
    Training trainingToRemove = trainings.stream()
        .filter(training -> training.getId().equals(trainingId))
        .findFirst()
        .orElse(null);
    if (trainingToRemove != null) {
      for (Exercise exercise : trainingToRemove.getExercises()) {
        exercise.getTrainings().remove(trainingToRemove);
      }
      // Perform actions to remove the exercise
      userData.getTrainings().remove(trainingToRemove);
      trainingRepository.delete(trainingToRemove);
      userDataService.saveUser(userData);
    }
    else {
      System.out.println("not found Exercise");
    }
  }

  public String getExercisesTypes(Long userId, Long trainingId) {
    UserData userData = userDataService.findUserById(userId);
    List<Training> trainings = userData.getTrainings();
    Training trainingFound = trainings.stream()
        .filter(training -> training.getId().equals(trainingId))
        .findFirst()
        .orElse(null);
    if (trainingFound != null) {
      List<Exercise> exerciseList = trainingFound.getExercises();

      return exerciseList.stream()
          .map(Exercise::getType)
          .collect(Collectors.joining(", "));
    }
    else {
      System.out.println("not found Exercise");
      return null;
    }
  }
}
