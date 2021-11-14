package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutDTO {

    private String token;

    private Boolean removed;
}
