package com.teo.gestor.domain.usecases.user;

public interface DeleteUserUseCase {

  String handle(String accountId, String password);

}