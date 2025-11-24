package com.teo.gestor.application.service.movement;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.MovementType;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.entity.StockMovementEntity;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.infrastructure.persistence.repository.StockMovementRepository;
import com.teo.gestor.presentation.dto.StockMovementRequestDTO;
import com.teo.gestor.presentation.dto.StockMovementResponseDTO;

@Service
public class StockMovementService {

  private final StockMovementRepository movementRepository;
  private final ProductRepository productRepository;

  public StockMovementService(StockMovementRepository movementRepository, ProductRepository productRepository) {
    this.movementRepository = movementRepository;
    this.productRepository = productRepository;
  }

  public List<StockMovementResponseDTO> findAll(String filter, String value) {
    List<StockMovementEntity> movements;
    if ("TYPE".equalsIgnoreCase(filter)) {
      movements = movementRepository.findByType(MovementType.valueOf(value));
    } else {
      movements = movementRepository.findAll();
    }
    return movements.stream().map(this::toResponseDTO).collect(Collectors.toList());
  }

  public void create(StockMovementRequestDTO data) {
    ProductEntity product = productRepository.findById(data.productId())
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    int currentQty = product.getQuantity() != null ? product.getQuantity().intValue() : 0;
    int newQty = currentQty;

    if (data.type() == MovementType.IN) {
      newQty += data.quantity();
    } else {
      if (currentQty < data.quantity())
        throw new RuntimeException("Estoque insuficiente.");
      newQty -= data.quantity();
    }

    product.setQuantity(newQty);
    productRepository.save(product);

    StockMovementEntity movement = new StockMovementEntity();
    movement.setProductId(data.productId());
    movement.setType(data.type());
    movement.setQuantity(data.quantity());
    movement.setReason(data.reason());
    movement.setDate(Instant.now());
    movementRepository.save(movement);
  }

  private StockMovementResponseDTO toResponseDTO(StockMovementEntity movement) {
    String productName = productRepository.findById(movement.getProductId())
        .map(ProductEntity::getName)
        .orElse("Produto Removido ou Desconhecido");

    return new StockMovementResponseDTO(
        movement.getId(),
        movement.getProductId(),
        productName,
        movement.getType(),
        movement.getQuantity(),
        movement.getReason(),
        movement.getDate());
  }

}
