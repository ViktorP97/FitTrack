package com.vp.fittrack.dtos;

import com.vp.fittrack.models.Training;

public class TrainingResponse {

  private Training training;
  private String exercises;

  private String exercisesTypes;

  public TrainingResponse(Training training, String exercises, String exercisesTypes) {
    this.training = training;
    this.exercises = exercises;
    this.exercisesTypes = exercisesTypes;
  }

  public Training getTraining() {
    return training;
  }

  public void setTraining(Training training) {
    this.training = training;
  }

  public String getExercises() {
    return exercises;
  }

  public void setExercises(String exercises) {
    this.exercises = exercises;
  }

  public String getExercisesTypes() {
    return exercisesTypes;
  }

  public void setExercisesTypes(String exercisesTypes) {
    this.exercisesTypes = exercisesTypes;
  }
}
