package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.ConfirmationToken;
import com.proiect.licenta.model.User;
import com.proiect.licenta.model.UserDetailsImpl;
import com.proiect.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        return UserDetailsImpl.build(user);
    }

    public String handleConfirmationToken(User user) {

        var token = UUID.randomUUID().toString();

        var confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {

        userRepository.enableUser(email);
    }

    public void updatePassword(String username, String password) {

        userRepository.updatePassword(username, password);
    }
}
