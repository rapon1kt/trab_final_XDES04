package com.teo.gestor.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.supplier.DeleteSupplierService;
import com.teo.gestor.application.service.supplier.FindSuppliersService;
import com.teo.gestor.application.service.supplier.RegisterSupplierService;
import com.teo.gestor.application.service.supplier.UpdateSupplierService;
import com.teo.gestor.domain.model.SupplierFilter;
import com.teo.gestor.presentation.dto.supplier.RegisterSupplierRequestDTO;
import com.teo.gestor.presentation.dto.supplier.UpdateSupplierRequestDTO;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

  private final RegisterSupplierService registerSupplierService;
  private final UpdateSupplierService updateSupplierService;
  private final FindSuppliersService findSuppliersService;
  private final DeleteSupplierService deleteSupplierService;

  public SupplierController(RegisterSupplierService registerSupplierService,
      UpdateSupplierService updateSupplierService, FindSuppliersService findSuppliersService,
      DeleteSupplierService deleteSupplierService) {
    this.registerSupplierService = registerSupplierService;
    this.updateSupplierService = updateSupplierService;
    this.findSuppliersService = findSuppliersService;
    this.deleteSupplierService = deleteSupplierService;
  }

  @PostMapping
  public ResponseEntity<?> register(@RequestBody @Valid RegisterSupplierRequestDTO requestDTO) {
    return ResponseEntity.status(201).body(this.registerSupplierService.handle(requestDTO));
  }

  @PatchMapping
  public ResponseEntity<?> update(@RequestParam("id") String id, @RequestBody UpdateSupplierRequestDTO requestDTO) {
    return ResponseEntity.ok(this.updateSupplierService.handle(id, requestDTO.getEnterpriseName(),
        requestDTO.getEmail(), requestDTO.getPhone()));
  }

  @GetMapping
  public ResponseEntity<?> find(@RequestParam("filter") SupplierFilter filter,
      @RequestParam("value") Optional<String> value) {
    return ResponseEntity.ok(this.findSuppliersService.handle(filter, value));
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@RequestParam("id") String id) {
    return ResponseEntity.ok(this.deleteSupplierService.handle(id));
  }

}
