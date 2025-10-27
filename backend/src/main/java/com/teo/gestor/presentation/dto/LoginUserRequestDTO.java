package com.teo.gestor.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginUserRequestDTO {
  @NotBlank(message = "Email é obrigatório!")
  @Email(message = "Formato de email inválido.")
  private String email;

  @NotBlank(message = "Senha é obrigatória!")
  @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres!")
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
