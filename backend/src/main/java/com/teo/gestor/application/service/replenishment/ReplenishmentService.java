package com.teo.gestor.application.service.replenishment;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teo.gestor.application.service.movement.StockMovementService;
import com.teo.gestor.domain.model.MovementType;
import com.teo.gestor.domain.model.ReplenishmentStatus;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.entity.ReplenishmentRequest;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.infrastructure.persistence.repository.ReplenishmentRepository;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.dto.ReplenishmentRequestDTO;
import com.teo.gestor.presentation.dto.ReplenishmentResponseDTO;
import com.teo.gestor.presentation.dto.StockMovementRequestDTO;

@Service
public class ReplenishmentService {

  private final ReplenishmentRepository repository;
  private final ProductRepository productRepository;
  private final SupplierRepository supplierRepository;
  private final StockMovementService stockMovementService; // Reuso!

  public ReplenishmentService(ReplenishmentRepository repository,
      ProductRepository productRepository,
      SupplierRepository supplierRepository,
      StockMovementService stockMovementService) {
    this.repository = repository;
    this.productRepository = productRepository;
    this.supplierRepository = supplierRepository;
    this.stockMovementService = stockMovementService;
  }

  public List<ReplenishmentResponseDTO> findAll(String filter, String value) {
    List<ReplenishmentRequest> requests;
    if ("STATUS".equalsIgnoreCase(filter)) {
      requests = repository.findByStatus(ReplenishmentStatus.valueOf(value));
    } else {
      requests = repository.findAll();
    }
    return requests.stream().map(this::toResponseDTO).collect(Collectors.toList());
  }

  @Transactional
  public void create(ReplenishmentRequestDTO data) {
    ReplenishmentRequest request = new ReplenishmentRequest(data.productId(), data.quantity());
    repository.save(request);
  }

  @Transactional
  public void updateStatus(String id, ReplenishmentStatus newStatus) {
    ReplenishmentRequest request = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    if (request.getStatus() != ReplenishmentStatus.OPEN) {
      throw new RuntimeException("Apenas pedidos ABERTOS podem ser alterados.");
    }

    request.setStatus(newStatus);
    request.setUpdatedAt(Instant.now());

    if (newStatus == ReplenishmentStatus.COMPLETED) {
      StockMovementRequestDTO movementData = new StockMovementRequestDTO(
          request.getProductId(),
          MovementType.IN,
          request.getQuantity(),
          "Pedido de Reposição #" + request.getId());
      stockMovementService.create(movementData);
    }

    repository.save(request);
  }

  private ReplenishmentResponseDTO toResponseDTO(ReplenishmentRequest req) {
    ProductEntity product = productRepository.findById(req.getProductId()).orElse(null);
    String productName = product != null ? product.getName() : "N/A";

    String supplierName = "N/A";
    if (product != null && product.getSupplierId() != null) {
      supplierName = supplierRepository.findById(product.getSupplierId())
          .map(SupplierEntity::getEnterpriseName).orElse("N/A");
    }

    return new ReplenishmentResponseDTO(
        req.getId(),
        req.getProductId(),
        productName,
        supplierName,
        req.getQuantity(),
        req.getStatus(),
        req.getCreatedAt());
  }
}