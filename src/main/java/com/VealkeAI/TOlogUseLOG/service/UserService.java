package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO createUser(UserDTO userToCreate);
    UserDTO getUser(Long id);
    UserDTO getByTelegramUser(Long id);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, UserDTO userToUpdate);
}
