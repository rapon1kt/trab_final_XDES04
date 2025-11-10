package com.teo.gestor.domain.usecases.user;

public interface UpdateUserUseCase {
  public String handle(String userId, String email, String password);
}
