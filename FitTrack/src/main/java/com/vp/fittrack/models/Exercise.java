package com.vp.fittrack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vp.fittrack.services.TrainingService;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private int duration;

  @Column(name = "deleted")
  private boolean deleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private UserData user;

  @ManyToMany(mappedBy = "exercises")
  @JsonIgnore
  private List<Training> trainings = new ArrayList<>();

  public Exercise() {

  }

  public Exercise(String name, String type) {
    this.name = name;
    this.type = type;
    this.duration = 0;
    this.deleted = false;
  }

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public UserData getUser() {
    return user;
  }

  public void setUser(UserData user) {
    this.user = user;
  }

  public List<Training> getTrainings() {
    return trainings;
  }

  public void setTrainings(List<Training> trainings) {
    this.trainings = trainings;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
