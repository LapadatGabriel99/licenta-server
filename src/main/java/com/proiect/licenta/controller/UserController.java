package com.proiect.licenta.controller;

import com.proiect.licenta.converter.LoginConverter;
import com.proiect.licenta.converter.UserConverter;
import com.proiect.licenta.dto.*;
import com.proiect.licenta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private LoginConverter loginConverter;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) {

        return new ResponseEntity<>(loginConverter.modelToDto(
                userService.login(userConverter.dtoToModel(userDTO))),
                HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) throws MessagingException {

        return new ResponseEntity<>(userConverter.modelToDto(
                userService.register(userConverter.dtoToModel(userDTO))),
                HttpStatus.CREATED);
    }

    @PutMapping("/update-user-details")
    public ResponseEntity<UserDTO> updateUserDetails(@Valid @RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(userConverter.modelToDto(
                userService.updateUserDetails(userConverter.dtoToModel(userDTO))),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {

        return new ResponseEntity<>(userService.confirmToken(token, false), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutDTO> logout(@Valid @RequestBody LogoutDTO dto) {

        dto.setRemoved(userService.logout(dto.getToken()));

        return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordRecoveryDTO> forgotPassword(@Valid @RequestBody PasswordRecoveryDTO dto) throws MessagingException {

        var user = userService.sendPasswordRecoveryMail(dto.getEmail());
        dto.setUsername(user);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<ResetPasswordDTO> resetPassword(@RequestParam("token") String token) {

        userService.confirmToken(token, true);

        return new ResponseEntity<>(new ResetPasswordDTO(true, "GUEST"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-password")
    public ResponseEntity updatePassword(@Valid @RequestBody UserDTO userDTO) {

        userService.updatePassword(userConverter.dtoToModel(userDTO));

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-user-details")
    public ResponseEntity<UserDTO> getUserDetails() {

        return new ResponseEntity<>(userConverter.modelToDto(userService.getUserDetails()), HttpStatus.OK);
    }

    @GetMapping("/refresh-user")
    public ResponseEntity<UserDTO> refreshUser() {

        return new ResponseEntity<>(userConverter.modelToDto(userService.refreshUser()), HttpStatus.OK);
    }
}
