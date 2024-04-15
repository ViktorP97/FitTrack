package com.vp.fittrack.dtos;

public class FoodDatabaseDto {

  private String name;

  private double proteins;

  private double carbs;

  private double fats;

  private double calories;

  public FoodDatabaseDto() {

  }

  public FoodDatabaseDto(String name, double proteins, double carbs, double fats, double calories) {
    this.name = name;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
    this.calories = calories;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
