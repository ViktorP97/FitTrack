package com.vp.fittrack.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.UserDataRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {

  @Mock
  private UserDataRepository userRepository;

  @InjectMocks
  private UserDataService userService;

  @Test
  public void testSaveUser() {
    UserData user = new UserData("John", "password", "john@example.com");

    userService.saveUser(user);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testExistingName() {
    String existingName = "existingName";
    UserData user = new UserData(existingName, "password", "email");
    userService.saveUser(user);

    boolean exists = userService.nameExist(existingName);

    assertFalse(exists, "Expected nameExist to return true for existing name");
  }

  @Test
  void testNonExistingName() {
    String nonExistingName = "nonExistingName";

    boolean exists = userService.nameExist(nonExistingName);

    assertFalse(exists, "Expected nameExist to return false for non-existing name");
  }

  @Test
  void testNotVerifiedUser() {
    String name = "notVerifiedUser";
    UserData user = new UserData(name, "password", "email");
    user.setVerified(false);
    when(userRepository.findByName(name)).thenReturn(Optional.of(user));

    boolean result = userService.notVerified(name);

    assertTrue(result, "Expected notVerified to return true for not verified user");
  }

  @Test
  void testVerifiedUser() {
    String name = "verifiedUser";
    UserData user = new UserData(name, "password", "email");
    user.setVerified(true);
    when(userRepository.findByName(name)).thenReturn(Optional.of(user));

    boolean result = userService.notVerified(name);

    assertFalse(result, "Expected notVerified to return false for verified user");
  }

}