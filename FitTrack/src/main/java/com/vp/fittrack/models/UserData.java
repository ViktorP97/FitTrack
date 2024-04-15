package com.vp.fittrack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vp.fittrack.security.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false)
  private boolean verified;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private boolean logedIn;

  private double desireCalories;

  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Exercise> exercises = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Training> trainings = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonIgnore
  private List<FoodItem> foods = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Activity> activities = new ArrayList<>();

  public UserData() {

  }
  public UserData(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
    this.verified = false;
    this.token = UUID.randomUUID().toString();
    this.logedIn = false;
    this.desireCalories = 0;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public void verifyUser() {
    verified = true;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isLogedIn() {
    return logedIn;
  }

  public void setLogedIn(boolean logedIn) {
    this.logedIn = logedIn;
  }

  public List<Exercise> getExercises() {
    return exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
  }

  public List<Training> getTrainings() {
    return trainings;
  }

  public void setTrainings(List<Training> trainings) {
    this.trainings = trainings;
  }

  public List<FoodItem> getFoods() {
    return foods;
  }

  public void setFoods(List<FoodItem> foods) {
    this.foods = foods;
  }

  public List<Activity> getActivities() {
    return activities;
  }

  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }

  public Role getRoleType() {
    return role;
  }

  public void setRoleType(Role role) {
    this.role = role;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public double getDesireCalories() {
    return desireCalories;
  }

  public void setDesireCalories(double desireCalories) {
    this.desireCalories = desireCalories;
  }
}
