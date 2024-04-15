package com.vp.fittrack.dtos;

public class FoodItemDto {

  private String name;

  private double proteins;

  private double carbs;

  private double fats;

  private double calories;

  private double grams;

  private String date;

  public FoodItemDto() {

  }

  public FoodItemDto(String name, double proteins, double carbs, double fats, double calories, double grams,
      String date) {
    this.name = name;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
    this.calories = calories;
    this.grams = grams;
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getGrams() {
    return grams;
  }

  public void setGrams(double grams) {
    this.grams = grams;
  }
}
