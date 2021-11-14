package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String token;

    private UserDTO userDTO;
}
