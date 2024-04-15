package com.vp.fittrack.dtos;

public class RegisterDto {

  private String name;
  private String password;
  private String email;

  public RegisterDto() {

  }
  public RegisterDto(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
