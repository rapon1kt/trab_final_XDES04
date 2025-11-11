package com.teo.gestor.application.service.supplier;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.dto.supplier.RegisterSupplierRequestDTO;
import com.teo.gestor.presentation.mapper.SupplierMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterSupplierServiceTest {

  @Mock
  private SupplierRepository supplierRepository;

  @Mock
  private SupplierMapper supplierMapper;

  @InjectMocks
  private RegisterSupplierService registerSupplierService;

  private RegisterSupplierRequestDTO createMockDTO() {
    return new RegisterSupplierRequestDTO(
        "Empresa Teste",
        "11.222.333/0001-44",
        "11999998888",
        "contato@teste.com",
        "01001-000",
        "SP",
        "São Paulo",
        "Rua Teste",
        123,
        "Bairro Teste");
  }

  @Test
  @DisplayName("Deve registrar um novo fornecedor com sucesso")
  void deveRegistrarFornecedor_QuandoCnpjNaoExiste() {
    RegisterSupplierRequestDTO dto = createMockDTO();

    when(supplierRepository.existsByCnpj(dto.getCnpj())).thenReturn(false);

    SupplierEntity entityToSave = new SupplierEntity();
    when(supplierMapper.toEntity(any(Supplier.class))).thenReturn(entityToSave);

    String result = registerSupplierService.handle(dto);

    assertThat(result).isEqualTo("Fornecedor cadastrado com sucesso!");

    ArgumentCaptor<SupplierEntity> supplierCaptor = ArgumentCaptor.forClass(SupplierEntity.class);
    verify(supplierRepository).save(supplierCaptor.capture());
  }

  @Test
  @DisplayName("Deve retornar mensagem de erro se o CNPJ já existir")
  void deveRetornarMensagemDeErro_QuandoCnpjJaExiste() {
    RegisterSupplierRequestDTO dto = createMockDTO();

    when(supplierRepository.existsByCnpj(dto.getCnpj())).thenReturn(true);

    String result = registerSupplierService.handle(dto);

    assertThat(result).isEqualTo("Já existe um fornecedor com esse cnpj!");

    verify(supplierRepository, never()).save(any(SupplierEntity.class));
  }
}