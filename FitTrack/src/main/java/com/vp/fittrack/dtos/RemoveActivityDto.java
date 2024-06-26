package com.vp.fittrack.dtos;

public class RemoveActivityDto {

  private double lat;

  private double lng;

  public RemoveActivityDto() {

  }

  public RemoveActivityDto(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
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
}
