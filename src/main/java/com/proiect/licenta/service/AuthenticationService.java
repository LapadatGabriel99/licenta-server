package com.proiect.licenta.service;

import com.proiect.licenta.model.LoginResponse;
import com.proiect.licenta.model.Role;
import com.proiect.licenta.model.User;
import com.proiect.licenta.model.UserDetailsImpl;
import com.proiect.licenta.repository.UserRepository;
import com.proiect.licenta.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public Optional<LoginResponse> authenticateUser(User user) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = jwtUtils.generateJwtToken(authentication);

        var userDetails = (UserDetailsImpl)authentication.getPrincipal();

        var userRoles = userDetails.getAuthorities()
                .stream()
                .map(item -> {

                    var role = new Role();

                    switch (item.getAuthority()) {

                        case "ROLE_USER":
                            role.setName("ROLE_USER");

                            return role;

                        case "ROLE_ADMIN":
                            role.setName("ROLE_ADMIN");

                            return role;
                    }

                    return null;
                })
                .collect(Collectors.toSet());

        user.setRoles(userRoles);

        user.setEmail(userDetails.getEmail());

        user.setId(userDetails.getId());

        user.setFirstname(userDetails.getFirstname());

        user.setLastname(userDetails.getLastname());

        user.setEnabled(userDetails.isEnabled());

        var loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setUser(user);

        return Optional.of(loginResponse);
    }

    @Transactional
    public User getCurrentUser() {

        var principal = (UserDetailsImpl)SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Transactional
    public User refreshCurrentUser() {

        var principal = (UserDetailsImpl)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        var user = new User();
        user.setId(principal.getId());
        user.setUsername(principal.getUsername());

        return user;
    }
}
