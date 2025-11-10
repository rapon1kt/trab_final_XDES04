package com.teo.gestor.presentation.dto;

import jakarta.validation.constraints.NotEmpty;

public class FindUserRequestDTO {

  @NotEmpty(message = "É preciso informar o filtro de pesquisa!")
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
