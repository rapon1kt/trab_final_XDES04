package com.teo.gestor.application.service.supplier;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.mapper.SupplierMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateSupplierServiceTest {

  @Mock
  private SupplierRepository supplierRepository;

  @Mock
  private SupplierMapper mapper;

  @InjectMocks
  private UpdateSupplierService updateSupplierService;

  @Test
  @DisplayName("Deve atualizar fornecedor com sucesso")
  void deveAtualizarFornecedorComSucesso() {
    String id = "supplier-id";
    SupplierEntity existingEntity = new SupplierEntity();
    Supplier existingDomain = new Supplier();
    existingDomain.setEnterpriseName("Old Name");
    existingDomain.setEmail("old@email.com");
    existingDomain.setPhone("11111");

    when(supplierRepository.findById(id)).thenReturn(Optional.of(existingEntity));
    when(mapper.toDomain(existingEntity)).thenReturn(existingDomain);

    String newName = "New Enterprise Name";
    String newEmail = "new@email.com";
    String newPhone = "99999";

    String result = updateSupplierService.handle(id, newName, newEmail, newPhone);

    assertThat(result).isEqualTo("Fornecedor foi atualizado com sucesso!");

    ArgumentCaptor<SupplierEntity> entityCaptor = ArgumentCaptor.forClass(SupplierEntity.class);
    verify(supplierRepository).save(entityCaptor.capture());

    assertThat(existingDomain.getEnterpriseName()).isEqualTo(newName);
    assertThat(existingDomain.getEmail()).isEqualTo(newEmail);
    assertThat(existingDomain.getPhone()).isEqualTo(newPhone);
  }

  @Test
  @DisplayName("Deve atualizar apenas os campos não nulos")
  void deveAtualizarApenasCamposNaoNulos() {
    String id = "supplier-id";
    SupplierEntity existingEntity = new SupplierEntity();
    Supplier existingDomain = new Supplier();
    existingDomain.setEnterpriseName("Old Name");
    existingDomain.setEmail("old@email.com");
    existingDomain.setPhone("11111");

    when(supplierRepository.findById(id)).thenReturn(Optional.of(existingEntity));
    when(mapper.toDomain(existingEntity)).thenReturn(existingDomain);

    String newName = "New Enterprise Name";

    updateSupplierService.handle(id, newName, null, null);

    ArgumentCaptor<SupplierEntity> entityCaptor = ArgumentCaptor.forClass(SupplierEntity.class);
    verify(supplierRepository).save(entityCaptor.capture());

    assertThat(existingDomain.getEnterpriseName()).isEqualTo(newName);
    assertThat(existingDomain.getEmail()).isEqualTo("old@email.com");
    assertThat(existingDomain.getPhone()).isEqualTo("11111");
  }

  @Test
  @DisplayName("Deve lançar exceção se fornecedor não for encontrado")
  void deveLancarExcecao_QuandoFornecedorNaoEncontrado() {
    String id = "not-found-id";
    when(supplierRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateSupplierService.handle(id, "Name", "Email", "Phone"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Fornecedor não encontrado!");
  }
}