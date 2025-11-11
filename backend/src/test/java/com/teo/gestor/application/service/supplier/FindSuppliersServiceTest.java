package com.teo.gestor.application.service.supplier;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.domain.model.SupplierFilter;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.mapper.SupplierMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FindSuppliersServiceTest {

  @Mock
  private SupplierRepository supplierRepository;

  @Mock
  private SupplierMapper supplierMapper;

  @InjectMocks
  private FindSuppliersService findSuppliersService;

  private SupplierEntity createMockEntity(String id) {
    SupplierEntity entity = new SupplierEntity();
    entity.setId(id);
    return entity;
  }

  private Supplier createMockDomain(String id) {
    Supplier domain = new Supplier();
    domain.setId(id);
    return domain;
  }

  @Test
  @DisplayName("Deve retornar todos os fornecedores quando o valor do filtro está vazio")
  void deveRetornarTodosOsFornecedores_QuandoValorNaoEstaPresente() {
    SupplierEntity entity1 = createMockEntity("1");
    SupplierEntity entity2 = createMockEntity("2");

    Supplier domain1 = createMockDomain("1");
    Supplier domain2 = createMockDomain("2");

    when(supplierRepository.findAll()).thenReturn(List.of(entity1, entity2));

    when(supplierMapper.toDomain(entity1)).thenReturn(domain1);
    when(supplierMapper.toDomain(entity2)).thenReturn(domain2);

    List<Supplier> result = findSuppliersService.handle(null, Optional.empty());

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(domain1, domain2);

    verify(supplierRepository).findAll();
  }

  @Test
  @DisplayName("Deve retornar um fornecedor pelo ID")
  void deveRetornarFornecedorPorId_QuandoFiltroEId() {
    String id = "test-id";
    SupplierEntity entity = createMockEntity(id);
    Supplier domain = createMockDomain(id);

    when(supplierRepository.findById(id)).thenReturn(Optional.of(entity));
    when(supplierMapper.toDomain(entity)).thenReturn(domain);

    List<Supplier> result = findSuppliersService.handle(SupplierFilter.ID, Optional.of(id));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(domain);

    verify(supplierRepository).findById(id);
  }

  @Test
  @DisplayName("Deve lançar exceção quando o ID do fornecedor não for encontrado")
  void deveLancarExcecao_QuandoFiltroEIdNaoEncontrado() {
    String id = "not-found-id";

    when(supplierRepository.findById(id)).thenReturn(Optional.empty());

    IllegalArgumentException exception = catchThrowableOfType(
        () -> findSuppliersService.handle(SupplierFilter.ID, Optional.of(id)),
        IllegalArgumentException.class);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Fornecedor não encontrado!");
  }

  @Test
  @DisplayName("Deve retornar fornecedores por CNPJ")
  void deveRetornarFornecedorPorCnpj_QuandoFiltroECnpj() {
    String cnpj = "123456789";
    SupplierEntity entity1 = createMockEntity("1");
    Supplier domain1 = createMockDomain("1");

    when(supplierRepository.findByCnpj(cnpj)).thenReturn(List.of(entity1));
    when(supplierMapper.toDomain(entity1)).thenReturn(domain1);

    List<Supplier> result = findSuppliersService.handle(SupplierFilter.CNPJ, Optional.of(cnpj));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(domain1);

    verify(supplierRepository).findByCnpj(cnpj);
  }
}