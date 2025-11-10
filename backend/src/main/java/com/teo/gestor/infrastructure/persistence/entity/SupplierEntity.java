package com.teo.gestor.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teo.gestor.domain.model.Address;

import lombok.Data;

@Data
@Document(collection = "suppliers")
public class SupplierEntity {
  @Id
  private String id;
  private String enterpriseName;
  private String cnpj;
  private String phone;
  private String email;
  private Address address;
}
