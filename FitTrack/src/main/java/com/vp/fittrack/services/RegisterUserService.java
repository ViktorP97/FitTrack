//package com.vp.fittrack.services;
//
//import com.vp.fittrack.dtos.RegisterDto;
//import com.vp.fittrack.models.UserData;
//import com.vp.fittrack.repositories.UserDataRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RegisterUserService {
//
//  private final UserDataRepository userRepository;
//
//  private final PasswordEncoder passwordEncoder;
//
//
//  @Autowired
//  public RegisterUserService(UserDataRepository userRepository, PasswordEncoder passwordEncoder) {
//    this.userRepository = userRepository;
//    this.passwordEncoder = passwordEncoder;
//  }
//
//  public UserData register(RegisterDto registerDto) {
//    UserData user = new UserData(registerDto.getName(), passwordEncoder.encode(registerDto.getPassword()), registerDto.getEmail());
//    userRepository.save(user);
//    System.out.println(user.getName());
//    return user;
//  }
//}
