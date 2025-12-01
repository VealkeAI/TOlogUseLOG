package com.VealkeAI.TOlogUseLOG.controller;

import com.VealkeAI.TOlogUseLOG.DTO.UserDTO;
import com.VealkeAI.TOlogUseLOG.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService service;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        var user = service.getUserById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("tg/{id}")
    public ResponseEntity<UserDTO> getUserByTgId(@PathVariable Long id) {

        var user = service.getUserByTgId(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO user) {

        service.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        service.deleteUser(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserDTO user) {

        var updatedUser = service.updateUser(id, user);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(updatedUser);
    }

    @PutMapping("{id}/utc/{shift}")
    public ResponseEntity<Void> setShiftUTC(@PathVariable Long id,
                                            @PathVariable int shift) {

        service.updateShitUTC(id, shift);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
