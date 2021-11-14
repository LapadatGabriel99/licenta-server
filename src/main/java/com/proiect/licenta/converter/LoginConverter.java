package com.proiect.licenta.converter;

import com.proiect.licenta.dto.LoginDTO;
import com.proiect.licenta.dto.UserDTO;
import com.proiect.licenta.model.LoginResponse;
import com.proiect.licenta.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginConverter {

    @Autowired
    private ModelMapper mapper;

    public LoginDTO modelToDto(LoginResponse loginResponse) {

        return mapper.map(loginResponse, LoginDTO.class);
    }

    public LoginResponse dtoToModel(LoginDTO loginDTO) {

        return mapper.map(loginDTO, LoginResponse.class);
    }
}
