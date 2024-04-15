package com.vp.fittrack.services;

import com.vp.fittrack.dtos.DesireCaloriesDto;
import com.vp.fittrack.dtos.RegisterDto;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.UserDataRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

  private final UserDataRepository userRepository;

  @Autowired
  public UserDataService(UserDataRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void saveUser(UserData user) {
    userRepository.save(user);
  }
  public UserData register(RegisterDto registerDto) {
    UserData user = new UserData(registerDto.getName(), registerDto.getPassword(), registerDto.getEmail());
    userRepository.save(user);
    return user;
  }

  public void verifyUser(String mail) {
    UserData user = userRepository.findByEmail(mail);
    user.setVerified(true);
    userRepository.save(user);
  }

  public boolean nameExist(String name) {
    List<UserData> allUsers = userRepository.findAll();
    if (allUsers != null && allUsers.size() > 0) {
      for (UserData user : allUsers) {
        if (user.getName().toLowerCase().equals(name.toLowerCase())) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean emailExist(String email) {
    List<UserData> allUsers = userRepository.findAll();
    if (allUsers != null && allUsers.size() > 0) {
      for (UserData user : allUsers) {
        if (user.getEmail().equals(email)) {
          return true;
        }
      }
    }
    return false;
  }

  public UserData findUserByToken(String token) {
    return userRepository.findByToken(token);
  }

  public boolean userRegistered(String name) {
    return userRepository.existsByName(name);
  }

  public boolean wrongPassword(String password, String name) {
    Optional<UserData> userOptional = userRepository.findByName(name);
    if (userOptional.isPresent()) {
      UserData user = userOptional.get();
      if (!user.getPassword().equals(password)) {
        return true;
      }
      return false;
    } else {
      return false;
    }
  }

  public boolean notVerified(String name) {
    Optional<UserData> userOptional = userRepository.findByName(name);
    if (userOptional.isPresent()) {
      UserData user = userOptional.get();
      if (!user.isVerified()) {
        return true;
      }
      return false;
    } else {
      return false;
    }
  }

  public UserData logInUser(String name) {
    Optional<UserData> userOptional = userRepository.findByName(name);
    if (userOptional.isPresent()) {
      UserData user = userOptional.get();
      user.setLogedIn(true);
      userRepository.save(user);
      return user;
    }
    return null;
  }

  public UserData findUserById(Long id) {
    Optional<UserData> userDataOption = userRepository.findById(id);
    if (userDataOption.isPresent()) {
      return userDataOption.get();
    } else {
      return null;
    }
  }

  public double saveCaloriesToUser(Long id, DesireCaloriesDto desireCaloriesDto) {
    Optional<UserData> userDataOptional = userRepository.findById(id);
    if (userDataOptional.isPresent()) {
      UserData userData = userDataOptional.get();
      userData.setDesireCalories(desireCaloriesDto.getDesireCalories());
      userRepository.save(userData);
      return desireCaloriesDto.getDesireCalories();
    } else {
      return 0;
    }
  }
}
