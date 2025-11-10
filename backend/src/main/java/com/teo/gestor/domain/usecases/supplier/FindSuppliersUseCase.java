package com.teo.gestor.domain.usecases.supplier;

import java.util.List;
import java.util.Optional;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.domain.model.SupplierFilter;

public interface FindSuppliersUseCase {
  public List<Supplier> handle(SupplierFilter filter, Optional<String> value);
}
