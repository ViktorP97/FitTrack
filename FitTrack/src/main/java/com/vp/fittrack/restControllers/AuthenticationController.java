//package com.vp.fittrack.restController;
//
//import com.vp.fittrack.dtos.AuthenticationRequest;
//import com.vp.fittrack.dtos.AuthenticationResponse;
//import com.vp.fittrack.dtos.LoginDto;
//import com.vp.fittrack.dtos.RegisterRequest;
//import com.vp.fittrack.models.UserData;
//import com.vp.fittrack.services.UserDataService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//
//  private final UserDataService userService;
//
//  private final UserDetailsService userDetailsService;
//
//
////  @PostMapping("/register2")
////  public ResponseEntity<AuthenticationResponse> register(
////      @RequestBody RegisterRequest request
////  ) {
////    return ResponseEntity.ok(service.register(request));
////  }
////
////  @PostMapping("/authenticate")
////  public ResponseEntity<AuthenticationResponse> register(
////      @RequestBody AuthenticationRequest request
////  ) {
////    return ResponseEntity.ok(service.authenticate(request));
////  }
//
//  @PostMapping("/login")
//  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
//    if (!userService.userRegistered(loginDto.getName())) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body("User not registered");
//    }
//    if (userService.wrongPassword(loginDto.getPassword(), loginDto.getName())) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body("Incorrect password");
//    }
//    if (userService.notVerified(loginDto.getName())) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body("User not verified");
//    }
//
//    UserData response = userService.logInUser(loginDto.getName());
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(response);
//  }
//
////  @PostMapping("/validate")
////  public ResponseEntity<String> validateToken(HttpServletRequest request) {
////    String token = userService.extractTokenFromHeader(request);
////    if (token == null) {
////      // Token not provided, return unauthorized status
////      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not provided");
////    }
////
////    String name = jwtService.extractUserName(token);
////    UserDetails userDetails = userDetailsService.loadUserByUsername(name);
////
////    if (!jwtService.isTokenValid(token, userDetails)) {
////      // Token invalid or expired, return unauthorized status
////      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalid or expired");
////    }
////
////    // Token is valid, return success status
////    return ResponseEntity.ok("Token is valid");
////  }
//}
