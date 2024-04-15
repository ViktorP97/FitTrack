package com.vp.fittrack.services;

import com.vp.fittrack.dtos.ActivityDto;
import com.vp.fittrack.dtos.RemoveActivityDto;
import com.vp.fittrack.models.Activity;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.ActivityRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityRepository activityRepository;

  private final UserDataService userDataService;

  @Autowired
  public ActivityService(ActivityRepository activityRepository, UserDataService userDataService) {
    this.activityRepository = activityRepository;
    this.userDataService = userDataService;
  }

  public Activity saveActivity(Long id, ActivityDto activityDto) {
    UserData user = userDataService.findUserById(id);
    Long timestamp = Long.parseLong(activityDto.getDate());
    Instant instant = Instant.ofEpochMilli(timestamp);
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    Activity activity = new Activity(activityDto.getName(), activityDto.getDistance(), activityDto.getDuration(), localDateTime, activityDto.getLat(), activityDto.getLng());
    activity.setUser(user);
    activityRepository.save(activity);
    user.getActivities().add(activity);
    userDataService.saveUser(user);
    return activity;
  }

  public List<Activity> findAllActivities(Long id) {
    UserData user = userDataService.findUserById(id);
    return user.getActivities();
  }

  public RemoveActivityDto removeActivity(Long userId, Long activityId) {
    RemoveActivityDto removeActivityDto = new RemoveActivityDto();
    UserData userData = userDataService.findUserById(userId);
    List<Activity> activities = userData.getActivities();
    Activity activityToRemove = activities.stream()
        .filter(activity -> activity.getId().equals(activityId))
        .findFirst()
        .orElse(null);
    if (activityToRemove != null) {
      removeActivityDto.setLat(activityToRemove.getLat());
      removeActivityDto.setLng(activityToRemove.getLng());
      userData.getActivities().remove(activityToRemove);
      activityRepository.delete(activityToRemove);
      userDataService.saveUser(userData);
      return removeActivityDto;
    } else {
      System.out.println("not found Activity");
      return null;
    }
  }
}
