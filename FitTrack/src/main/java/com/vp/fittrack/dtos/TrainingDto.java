package com.vp.fittrack.dtos;

public class TrainingDto {

  private String name;

  private String exerciseOrder;

  private String exerciseValues;

  private int rounds;

  public TrainingDto() {

  }

  public TrainingDto(String name, String exerciseOrder, String exerciseValues, int rounds) {
    this.name = name;
    this.exerciseOrder = exerciseOrder;
    this.exerciseValues = exerciseValues;
    this.rounds = rounds;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExerciseOrder() {
    return exerciseOrder;
  }

  public void setExerciseOrder(String exerciseOrder) {
    this.exerciseOrder = exerciseOrder;
  }

  public String getExerciseValues() {
    return exerciseValues;
  }

  public void setExerciseValues(String exerciseValues) {
    this.exerciseValues = exerciseValues;
  }

  public int getRounds() {
    return rounds;
  }

  public void setRounds(int rounds) {
    this.rounds = rounds;
  }
}
