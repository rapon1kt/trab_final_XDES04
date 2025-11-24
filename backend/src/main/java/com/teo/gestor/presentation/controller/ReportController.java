package com.teo.gestor.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.ReportService;
import com.teo.gestor.presentation.dto.ReportStockByCategoryDTO;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final ReportService service;

  public ReportController(ReportService service) {
    this.service = service;
  }

  @GetMapping("/stock-by-category")
  public ResponseEntity<List<ReportStockByCategoryDTO>> getStockByCategory() {
    return ResponseEntity.ok(service.getStockByCategory());
  }

}