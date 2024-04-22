package com.vp.fittrack.restControllers;

import com.vp.fittrack.dtos.ActivityDto;
import com.vp.fittrack.dtos.RemoveActivityDto;
import com.vp.fittrack.models.Activity;
import com.vp.fittrack.services.ActivityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityRestController {

  private final ActivityService activityService;

  @Autowired
  public ActivityRestController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @PostMapping("/save/activity/{id}")
  public ResponseEntity<Activity> saveActivity(@PathVariable Long id, ActivityDto activityDto) {
    Activity activity = activityService.saveActivity(id, activityDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(activity);
  }

  @PostMapping("/all/activities/{id}")
  public ResponseEntity<List<Activity>> getAllActivities(@PathVariable Long id) {
    List<Activity> activities = activityService.findAllActivities(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(activities);
  }

  @PostMapping("/{userId}/delete/activity/{activityId}")
  public ResponseEntity<RemoveActivityDto> removeActivity(@PathVariable Long userId, @PathVariable Long activityId) {
    RemoveActivityDto removeActivityDto = activityService.removeActivity(userId, activityId);
    return ResponseEntity.ok().body(removeActivityDto);
  }
}
