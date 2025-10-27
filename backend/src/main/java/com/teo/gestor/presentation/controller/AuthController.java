package com.teo.gestor.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.user.CreateUserService;
import com.teo.gestor.application.service.user.LoginUserService;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.presentation.dto.CreateUserRequestDTO;
import com.teo.gestor.presentation.dto.LoginUserRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/req")
public class AuthController {

  private final CreateUserService createUserService;
  private final LoginUserService loginUserService;

  public AuthController(CreateUserService createUserService, LoginUserService loginUserService) {
    this.createUserService = createUserService;
    this.loginUserService = loginUserService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid CreateUserRequestDTO requestDTO) {
    User newUser = this.createUserService.handle(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword());
    return ResponseEntity.status(201).body(newUser);
  }

  @PostMapping(value = "/signin", consumes = "application/json")
  public ResponseEntity<?> signin(@RequestBody @Valid LoginUserRequestDTO requestDTO) {
    return ResponseEntity.ok(this.loginUserService.handle(requestDTO.getEmail(), requestDTO.getPassword()));
  }

}
