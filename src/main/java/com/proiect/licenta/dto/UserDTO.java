package com.proiect.licenta.dto;

import com.proiect.licenta.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private Set<Role> roles;
}
