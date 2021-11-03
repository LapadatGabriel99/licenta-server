package com.proiect.licenta.service;

import com.proiect.licenta.model.ConfirmationToken;
import com.proiect.licenta.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {

        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
