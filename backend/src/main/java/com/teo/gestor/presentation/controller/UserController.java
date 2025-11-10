package com.teo.gestor.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.user.DeleteUserService;
import com.teo.gestor.application.service.user.UpdateUserService;
import com.teo.gestor.presentation.dto.DeleteUserRequestDTO;
import com.teo.gestor.presentation.dto.UpdateUserRequestDTO;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UpdateUserService updateUserService;
  private final DeleteUserService deleteUserService;

  public UserController(UpdateUserService updateUserService, DeleteUserService deleteUserService) {
    this.updateUserService = updateUserService;
    this.deleteUserService = deleteUserService;
  }

  @PatchMapping
  public ResponseEntity<?> update(@RequestParam("id") String userId,
      @RequestBody UpdateUserRequestDTO requestDTO) {
    return ResponseEntity.ok(this.updateUserService.handle(userId, requestDTO.getEmail(), requestDTO.getPassword()));
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@RequestParam("id") String userId,
      @RequestBody DeleteUserRequestDTO requestDTO) {
    return ResponseEntity.ok(this.deleteUserService.handle(userId, requestDTO.getPassword()));
  }

}
