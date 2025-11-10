package com.teo.gestor.domain.usecases;

import java.util.List;
import java.util.Optional;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.model.UserFilter;

public interface FindUsersUseCase {

  public List<User> handle(UserFilter filter, Optional<String> value);

}
