package com.teo.gestor.domain.model;

public record Address(
    String cep,
    String state,
    String city,
    String street,
    Number number,
    String district) {

}
