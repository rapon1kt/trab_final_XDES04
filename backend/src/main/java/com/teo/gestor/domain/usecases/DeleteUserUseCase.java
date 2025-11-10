package com.teo.gestor.domain.usecases;

public interface DeleteUserUseCase {

  String handle(String accountId, String password);

}