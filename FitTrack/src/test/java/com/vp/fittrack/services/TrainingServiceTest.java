package com.vp.fittrack.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.vp.fittrack.dtos.TrainingDto;
import com.vp.fittrack.exceptions.TrainingNotFoundException;
import com.vp.fittrack.exceptions.UserNotFoundException;
import com.vp.fittrack.models.Exercise;
import com.vp.fittrack.models.Training;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.ExerciseRepository;
import com.vp.fittrack.repositories.TrainingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

  @Mock
  private UserDataService userDataService;

  @Mock
  private ExerciseService exerciseService;

  @Mock
  private TrainingRepository trainingRepository;

  @Mock
  private ExerciseRepository exerciseRepository;

  @InjectMocks
  private TrainingService trainingService;

  @Test
  void findTrainingByIdWhenTrainingFound() {
    Training training = new Training();
    training.setName("TestTraining");
    when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

    Training foundTraining = trainingService.findTrainingById(1L);
    assertEquals(training, foundTraining);
  }

  @Test
  void findTrainingByIdWhenTrainingNotFound() {
    when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(
        TrainingNotFoundException.class, () -> trainingService.findTrainingById(1L));
  }

  @Test
  void testSaveTrainingWhenUserExists() {
    Long userId = 1L;
    TrainingDto trainingDto = new TrainingDto();
    trainingDto.setName("TestTraining");
    trainingDto.setExerciseOrder("1,2");
    trainingDto.setExerciseValues("10,20");
    trainingDto.setRounds(3);

    UserData user = new UserData("Test", "test", "test@test.com");
    when(userDataService.findUserById(userId)).thenReturn(user);

    List<Exercise> exercises = new ArrayList<>();
    when(exerciseService.findExercisesById(trainingDto.getExerciseOrder())).thenReturn(exercises);

    Training savedTraining = new Training();
    savedTraining.setName("TestTraining");
    savedTraining.setExerciseOrder("1,2");
    savedTraining.setExerciseValues("10,20");
    savedTraining.setRounds(3);
    savedTraining.setUser(user);
    savedTraining.setExercises(exercises);
    when(trainingRepository.save(any())).thenReturn(savedTraining);

    Training result = trainingService.saveTraining(userId, trainingDto);

    assertEquals(savedTraining.getUser(), result.getUser());
    assertEquals(savedTraining.getName(), result.getName());
    assertEquals(savedTraining.getExercises().size(), result.getExercises().size());

    verify(trainingRepository, times(1)).save(result);
    verify(exerciseRepository, times(1)).saveAll(exercises);
    verify(userDataService, times(1)).saveUser(user);
  }

  @Test
  void testSaveTrainingWhenUserNotExists() {
    Long userId = 1L;
    TrainingDto trainingDto = new TrainingDto();
    trainingDto.setName("TestTraining");
    trainingDto.setExerciseOrder("1,2");
    trainingDto.setExerciseValues("10,20");
    trainingDto.setRounds(3);

    doThrow(UserNotFoundException.class).when(userDataService).findUserById(userId);

    assertThrows(UserNotFoundException.class, () -> {
      trainingService.saveTraining(userId, trainingDto);
    });

    verifyNoInteractions(trainingRepository, exerciseRepository);
    verify(userDataService, never()).saveUser(any(UserData.class));
  }
}