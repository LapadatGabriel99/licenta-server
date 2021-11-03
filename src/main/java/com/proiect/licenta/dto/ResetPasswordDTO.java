package com.proiect.licenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordDTO {

    private boolean isAuthorized;

    private String role;
}