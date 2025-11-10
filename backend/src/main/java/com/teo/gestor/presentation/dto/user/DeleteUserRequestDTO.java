package com.teo.gestor.presentation.dto.user;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequestDTO {

  @NotBlank(message = "É obrigatório informar a senha do administrador!")
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
