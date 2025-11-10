package com.teo.gestor.domain.usecases.user;

import com.teo.gestor.domain.model.User;

public interface CreateUserUseCase {
  public User handle(String name, String email, String password);
}
