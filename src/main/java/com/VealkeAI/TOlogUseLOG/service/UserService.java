package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.user.ObtainedUserDTO;
import com.VealkeAI.TOlogUseLOG.DTO.user.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(ObtainedUserDTO userToCreate);
    UserDTO getUserById(Long id);
    UserDTO getUserByTgId(Long id);
    void deleteUser(Long id);
    void updateShitUTC(Long id, int shift);
}
