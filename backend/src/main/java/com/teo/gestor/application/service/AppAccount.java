package com.teo.gestor.application.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class AppAccount implements UserDetailsService {

  private final UserRepository userRepository;

  public AppAccount(UserRepository userRepository, UserMapper mapper) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = this.userRepository.findByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Esse usuário não existe."));
    return User.builder().username(userEntity.getName())
        .password(userEntity.getPassword()).build();
  }

  public String getAccountIdByName(String name) {
    UserEntity user = this.userRepository.findByName(name)
        .orElseThrow(() -> new UsernameNotFoundException("Esse usuário não existe."));
    return user.getId();
  }

}
