package com.teo.gestor.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.movement.StockMovementService;
import com.teo.gestor.presentation.dto.StockMovementRequestDTO;
import com.teo.gestor.presentation.dto.StockMovementResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stock-movements")
public class StockMovementController {

  private final StockMovementService service;

  public StockMovementController(StockMovementService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<StockMovementResponseDTO>> findAll(
      @RequestParam(name = "filter", defaultValue = "ALL") String filter,
      @RequestParam(name = "value", required = false) String value) {
    return ResponseEntity.ok(service.findAll(filter, value));
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody @Valid StockMovementRequestDTO data) {
    service.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}