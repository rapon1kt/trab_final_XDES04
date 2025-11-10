package com.teo.gestor.domain.model;

import java.time.Instant;

public class User {
  private String id;
  private String name;
  private String email;
  private String password;
  private Role role;
  private boolean active;
  private Instant createdAt;
  private Instant modifiedAt;

  public User() {
  }

  public User(String id, String name, String email, String password, Role role, boolean active,
      Instant createdAt, Instant modifiedAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.active = active;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static User create(String name, String email, String password) {
    User newUser = new User();
    newUser.setName(name);
    newUser.setEmail(email);
    newUser.setPassword(password);
    newUser.setActive(true);
    newUser.setCreatedAt(Instant.now());
    newUser.setModifiedAt(Instant.now());
    newUser.setRole(Role.OPERATOR);
    return newUser;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

}
