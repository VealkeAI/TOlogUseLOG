package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.user.ObtainedUserDTO;
import com.VealkeAI.TOlogUseLOG.DTO.user.UserDTO;
import com.VealkeAI.TOlogUseLOG.DTO.mapper.UserMapper;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import com.VealkeAI.TOlogUseLOG.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createUser(ObtainedUserDTO userToCreate) {

        if (userRepository.findByTgId(userToCreate.tgId()).isEmpty())
        {
            userRepository.save(userMapper.toEntity(userToCreate));
        }
    }

    @Override
    public UserDTO getUserById(Long id) {

        var user = userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by id: " + id)
                );

        return userMapper.toDomain(user);
    }

    @Override
    public UserDTO getUserByTgId(Long id) {

        var user = userRepository.findByTgId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by telegram id: " + id)
                );

        return userMapper.toDomain(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

        var user = userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by id: " + id)
                );

        userRepository.delete(user);
    }

    @Override
    @Transactional
    @Deprecated
    public UserDTO updateUser(Long id, ObtainedUserDTO userToUpdate) {

        var oldUser = userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by id: " + id)
                );

        var userToSave = userMapper.toEntity(userToUpdate);
        userToSave.setId(id);
        userToSave.setListOfTask(oldUser.getListOfTask());

        var updatedUser = userRepository.save(userToSave);

        return userMapper.toDomain(updatedUser);
    }

    @Override
    @Transactional
    public void updateShitUTC(Long id, int shift) {

        userRepository.findByTgId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by telegram id: " + id)
                );

        userRepository.switchShiftUTC(id, shift);
    }
}
