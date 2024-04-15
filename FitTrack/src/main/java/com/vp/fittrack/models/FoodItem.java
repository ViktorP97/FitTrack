package com.vp.fittrack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "foods")
public class FoodItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private double proteins;

  private double carbs;

  private double fats;

  private double calories;

  private double grams;

  private LocalDate date;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private UserData user;

  public FoodItem() {

  }

  public FoodItem(String name, double proteins, double carbs, double fats, double calories, double grams) {
    this.name = name;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
    this.calories = calories;
    this.grams = grams;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getProteins() {
    return proteins;
  }

  public void setProteins(double proteins) {
    this.proteins = proteins;
  }

  public double getCarbs() {
    return carbs;
  }

  public void setCarbs(double carbs) {
    this.carbs = carbs;
  }

  public double getFats() {
    return fats;
  }

  public void setFats(double fats) {
    this.fats = fats;
  }

  public double getCalories() {
    return calories;
  }

  public void setCalories(double calories) {
    this.calories = calories;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public UserData getUser() {
    return user;
  }

  public void setUser(UserData user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getGrams() {
    return grams;
  }

  public void setGrams(double grams) {
    this.grams = grams;
  }
}
