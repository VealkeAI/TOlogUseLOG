package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UserDTO userToCreate);
    UserDTO getUserById(Long id);
    UserDTO getUserByTgId(Long id);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, UserDTO userToUpdate);
}
