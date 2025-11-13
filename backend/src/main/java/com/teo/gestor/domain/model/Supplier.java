package com.teo.gestor.domain.model;

public class Supplier {
  private String id;
  private String enterpriseName;
  private String cnpj;
  private String phone;
  private boolean active;
  private String email;
  private Address address;

  public Supplier() {

  }

  public Supplier(String id, String enterpriseName, String cnpj, String phone, boolean active, String email,
      Address address) {
    this.id = id;
    this.enterpriseName = enterpriseName;
    this.cnpj = cnpj;
    this.phone = phone;
    this.active = active;
    this.email = email;
    this.address = address;
  }

  public static Supplier create(String enterpriseName, String cpnj, String phone, String email, Address address) {
    Supplier supplier = new Supplier();
    supplier.setEnterpriseName(enterpriseName);
    supplier.setCnpj(cpnj);
    supplier.setPhone(phone);
    supplier.setActive(true);
    supplier.setEmail(email);
    supplier.setAddress(address);
    return supplier;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}
