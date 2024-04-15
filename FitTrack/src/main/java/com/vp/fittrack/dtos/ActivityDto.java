package com.vp.fittrack.dtos;

public class ActivityDto {

  private String name;

  private double lat;

  private double lng;

  private double distance;

  private double duration;

  private String date;

  public ActivityDto() {

  }

  public ActivityDto(String name, double lat, double lng, double distance, double time,
      String date) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.distance = distance;
    this.duration = time;
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public double getDuration() {
    return duration;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
