package com.vp.fittrack.services;

import com.vp.fittrack.exceptions.ExerciseNameExistException;
import com.vp.fittrack.dtos.ExerciseDto;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.ExerciseRepository;
import com.vp.fittrack.repositories.TrainingRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseService {

  private final ExerciseRepository exerciseRepository;

  private final TrainingRepository trainingRepository;

  private final UserDataService userDataService;

  @Autowired
  public ExerciseService(ExerciseRepository exerciseRepository, TrainingRepository trainingRepository,
      UserDataService userDataService) {
    this.exerciseRepository = exerciseRepository;
    this.trainingRepository = trainingRepository;
    this.userDataService = userDataService;
  }

  public Exercise saveExercise(ExerciseDto exerciseDto, Long id) {
    UserData user = userDataService.findUserById(id);
    Exercise exercise = new Exercise(exerciseDto.getName(),exerciseDto.getType());
    if (exerciseNameExist(exercise.getName(), id)) {
      throw new ExerciseNameExistException("Exercise with this name already exist.");
    }
    exercise.setUser(user);
    exerciseRepository.save(exercise);
    user.getExercises().add(exercise);
    userDataService.saveUser(user);
    return exercise;
  }

  public List<Exercise> getAllExercises() {
    return exerciseRepository.findAll();
  }

  public List<Exercise> findExercisesById(String exerciseOrder) {
    List<Exercise> exercises = getAllExercises();
    List<Long> orderList = Arrays.stream(exerciseOrder.split(","))
        .map(Long::parseLong)
        .collect(Collectors.toList());
    return findOrder(orderList, exercises);
  }

  public List<Exercise> putExercisesToTheOrder(String order, List<Exercise> exercises) {
    List<Long> orderList = Arrays.stream(order.split(","))
        .map(Long::parseLong)
        .collect(Collectors.toList());
    return findOrder(orderList, exercises);
  }

  public List<Exercise> findOrder(List<Long> orderList, List<Exercise> exercises) {
    List<Exercise> result = new ArrayList<>();
    for (int i = 0; i < orderList.size(); i++) {
      for (int j = 0; j < exercises.size(); j++) {
        if (Objects.equals(exercises.get(j).getId(), orderList.get(i))) {
          result.add(exercises.get(j));
          break;
        }
      }
    }
    return result;
  }
  public void addTimeToExercises(Long id, String times) {
    Optional<Training> setOptional = trainingRepository.findById(id);
    if (setOptional.isPresent()) {
      Training training = setOptional.get();
      training.setExerciseValues(times);
      trainingRepository.save(training);
    } else {
      System.out.println("error");
    }
  }

  public List<Exercise> getExercisesForPractice(Long id) {
    List<Exercise> exercisesForPractice = new ArrayList<>();
    Optional<Training> trainingOptional = trainingRepository.findById(id);
    if (trainingOptional.isPresent()) {
      Training training = trainingOptional.get();
      List<Exercise> exercises = putExercisesToTheOrder(training.getExerciseOrder(), training.getExercises());
      List<Integer> valuesList = Arrays.stream(training.getExerciseValues().split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

      for (int i = 0; i < exercises.size(); i++) {
          exercises.get(i).setDuration(valuesList.get(i));
          exercisesForPractice.add(exercises.get(i));
      }
      return exercisesForPractice;
    } else {
      System.out.println("error");
      return null;
    }
  }

  @Transactional
  public void removeExercise(Long userId, Long exerciseId) {
    UserData userData = userDataService.findUserById(userId);
    List<Exercise> exercises = userData.getExercises();
    Exercise exerciseToRemove = exercises.stream()
        .filter(exercise -> exercise.getId().equals(exerciseId))
        .findFirst()
        .orElse(null);
    if (exerciseToRemove != null) {
      exerciseToRemove.setDeleted(true);
      exerciseRepository.save(exerciseToRemove);
      userDataService.saveUser(userData);
      System.out.println(userData.getExercises().size());
    }
    else {
      System.out.println("not found Exercise");
    }
  }

  public boolean exerciseNameExist(String exerciseName, Long userId) {
    String editedExerciseName = exerciseName.toLowerCase();
    UserData user = userDataService.findUserById(userId);
    List<Exercise> exercises = user.getExercises();
    if (exercises != null && exercises.size() > 0) {
      for (Exercise exercise : exercises) {
        if(exercise.getName().toLowerCase().equals(editedExerciseName) && !exercise.isDeleted()) {
          return true;
        }
      }
    }
    return false;
  }
}
