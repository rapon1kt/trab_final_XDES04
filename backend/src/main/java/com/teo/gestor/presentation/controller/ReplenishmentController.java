package com.teo.gestor.presentation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.replenishment.ReplenishmentService;
import com.teo.gestor.domain.model.ReplenishmentStatus;
import com.teo.gestor.presentation.dto.ReplenishmentRequestDTO;
import com.teo.gestor.presentation.dto.ReplenishmentResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/replenishments")
public class ReplenishmentController {

  private final ReplenishmentService service;

  public ReplenishmentController(ReplenishmentService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ReplenishmentResponseDTO>> findAll(
      @RequestParam(name = "filter", defaultValue = "ALL") String filter,
      @RequestParam(name = "value", required = false) String value) {
    return ResponseEntity.ok(service.findAll(filter, value));
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody @Valid ReplenishmentRequestDTO data) {
    service.create(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateStatus(
      @PathVariable String id,
      @RequestBody Map<String, String> payload) {
    String statusStr = payload.get("status");
    service.updateStatus(id, ReplenishmentStatus.valueOf(statusStr));
    return ResponseEntity.ok().build();
  }
}
