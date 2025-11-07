package com.teo.gestor.domain.usecases;

public interface UpdateUserUseCase {
  public String handle(String userId, String email, String password);
}
