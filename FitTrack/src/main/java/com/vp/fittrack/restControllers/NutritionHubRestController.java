package com.vp.fittrack.restControllers;

import com.vp.fittrack.exceptions.UserNotFoundException;
import com.vp.fittrack.dtos.DesireCaloriesDto;
import com.vp.fittrack.dtos.FoodItemDto;
import com.vp.fittrack.models.FoodItem;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.services.FoodItemService;
import com.vp.fittrack.services.UserDataService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionHubRestController {

  private final UserDataService userDataService;

  private final FoodItemService foodItemService;


  public NutritionHubRestController(UserDataService userDataService,
      FoodItemService foodItemService) {
    this.userDataService = userDataService;
    this.foodItemService = foodItemService;
  }

  @PostMapping("/user/calories/{id}")
  public ResponseEntity<Double> getDesireCalories(@PathVariable Long id) {
    UserData userData = userDataService.findUserById(id);
    return ResponseEntity.ok(userData.getDesireCalories());
  }

  @PostMapping("/add/calories/{id}")
  public ResponseEntity<Double> addDesireCalories(@PathVariable Long id, DesireCaloriesDto desireCaloriesDto) {
    try {
      double calories = userDataService.saveCaloriesToUser(id, desireCaloriesDto);
      return ResponseEntity.ok(calories);
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/save-food/{id}")
  public ResponseEntity<FoodItem> saveFood(@PathVariable Long id, FoodItemDto foodItemDto) {
    FoodItem food = foodItemService.saveFood(id, foodItemDto);
    return ResponseEntity.ok(food);
  }

  @PostMapping("/find-foods/{id}")
  public ResponseEntity<List<FoodItem>> findUsersFoods(@PathVariable Long id, @RequestBody String date) {
    List<FoodItem> foodItems = foodItemService.findUsersFoodForChosenDate(id, date);
    return ResponseEntity.ok(foodItems);
  }

  @PostMapping("/{userId}/remove-food/{foodId}")
  public ResponseEntity<List<FoodItem>> removeFood(@PathVariable Long userId, @PathVariable Long foodId) {
    List<FoodItem> foods = foodItemService.removeFood(userId, foodId);
    return ResponseEntity.ok(foods);
  }
}
