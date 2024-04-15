package com.vp.fittrack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainings")
public class Training {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private UserData user;

  private String exerciseOrder;

  private String exerciseValues;

  private LocalDateTime timeOfCreation;

  @ManyToMany
  @JoinTable(
      name = "exercise_training",
      joinColumns = @JoinColumn(name = "training_id"),
      inverseJoinColumns = @JoinColumn(name = "exercise_id")
  )
  @JsonIgnore
  private List<Exercise> exercises = new ArrayList<>();

  private int rounds;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserData getUser() {
    return user;
  }

  public void setUser(UserData user) {
    this.user = user;
  }

  public String getExerciseOrder() {
    return exerciseOrder;
  }

  public void setExerciseOrder(String exerciseOrder) {
    this.exerciseOrder = exerciseOrder;
  }

  public int getRounds() {
    return rounds;
  }

  public void setRounds(int rounds) {
    this.rounds = rounds;
  }

  public String getExerciseValues() {
    return exerciseValues;
  }

  public void setExerciseValues(String exerciseValues) {
    this.exerciseValues = exerciseValues;
  }

  public List<Exercise> getExercises() {
    return exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
  }

  public LocalDateTime getTimeOfCreation() {
    return timeOfCreation;
  }

  public void setTimeOfCreation(LocalDateTime timeOfCreation) {
    this.timeOfCreation = timeOfCreation;
  }
}

