package com.teo.gestor.presentation.dto.supplier;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterSupplierRequestDTO {

  @NotEmpty(message = "O nome da empresa é obrigatório!")
  private String enterpriseName;
  @CNPJ(message = "Formato de CNPJ inválido!")
  @NotEmpty(message = "O CNPJ é obrigatório!")
  private String cnpj;
  @NotEmpty(message = "O telefone é obrigatório!")
  private String phone;
  @Email(message = "Formato de email inválido!")
  @NotEmpty(message = "O email é obrigatório!")
  private String email;

  @NotEmpty(message = "CEP é obrigatório!")
  private String cep;
  @NotEmpty(message = "O estado é obrigatório!")
  private String state;
  @NotEmpty(message = "A cidade é obrigatória!")
  private String city;
  @NotEmpty(message = "A rua é obrigatória!")
  private String street;
  @NotEmpty(message = "O número do estabelecimento é obrigatório!")
  private Number number;
  @NotEmpty(message = "O bairro é obrigatório!")
  private String district;

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public RegisterSupplierRequestDTO(@NotEmpty(message = "O nome da empresa é obrigatório!") String enterpriseName,
      @CNPJ(message = "Formato de CNPJ inválido!") @NotEmpty(message = "O CNPJ é obrigatório!") String cnpj,
      @NotEmpty(message = "O telefone é obrigatório!") String phone,
      @Email(message = "Formato de email inválido!") @NotEmpty(message = "O email é obrigatório!") String email,
      @NotEmpty(message = "CEP é obrigatório!") String cep, @NotEmpty(message = "O estado é obrigatório!") String state,
      @NotEmpty(message = "A cidade é obrigatória!") String city,
      @NotEmpty(message = "A rua é obrigatória!") String street,
      @NotEmpty(message = "O número do estabelecimento é obrigatório!") Number number,
      @NotEmpty(message = "O bairro é obrigatório!") String district) {
    this.enterpriseName = enterpriseName;
    this.cnpj = cnpj;
    this.phone = phone;
    this.email = email;
    this.cep = cep;
    this.state = state;
    this.city = city;
    this.street = street;
    this.number = number;
    this.district = district;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Number getNumber() {
    return number;
  }

  public void setNumber(Number number) {
    this.number = number;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

}
