package com.lafin.userservice.service;

import com.lafin.userservice.dto.UserDto;
import com.lafin.userservice.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String email);
    Iterable<UserEntity> getUserByAll();
}
