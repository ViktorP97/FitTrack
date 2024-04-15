package com.vp.fittrack.restController;

import com.vp.fittrack.Exceptions.FoodNameExistException;
import com.vp.fittrack.dtos.FoodDatabaseDto;
import com.vp.fittrack.models.FoodDatabase;
import com.vp.fittrack.services.FoodDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodDatabaseRestController {

  private final FoodDatabaseService foodDatabaseService;

  @Autowired
  public FoodDatabaseRestController(FoodDatabaseService foodDatabaseService) {
    this.foodDatabaseService = foodDatabaseService;
  }

  @PostMapping("/add-to-database")
  public ResponseEntity<?> addFoodToDatabase(FoodDatabaseDto foodDatabaseDto) {
    try {
      FoodDatabase foodDatabase = foodDatabaseService.saveFoodToDatabase(foodDatabaseDto);
      return ResponseEntity.ok(foodDatabase);
    } catch (FoodNameExistException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Food already exist");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/find-food")
  public ResponseEntity<FoodDatabase> findFood(@RequestBody String name) {
    FoodDatabase foodDatabase = foodDatabaseService.findFoodByName(name);
    if (foodDatabase == null) {
      return ResponseEntity.status(204).body(null);
    }
    return ResponseEntity.ok(foodDatabase);
  }
}
