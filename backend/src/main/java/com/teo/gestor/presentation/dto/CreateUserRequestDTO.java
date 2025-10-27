package com.teo.gestor.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequestDTO {

  @NotBlank(message = "Nome é obrigatório!")
  @Size(min = 4, message = "Nome deve ter pelo menos 4 caracteres!")
  private String name;

  @NotBlank(message = "Email é obrigatório!")
  @Email(message = "Formato de email inválido.")
  private String email;

  @NotBlank(message = "Senha é obrigatória!")
  @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres!")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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
