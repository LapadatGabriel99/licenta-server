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

        var loginDTO = mapper.map(loginResponse, LoginDTO.class);
        loginDTO.setUserDTO(mapper.map(loginResponse.getUser(), UserDTO.class));

        return loginDTO;
    }

    public LoginResponse dtoToModel(LoginDTO loginDTO) {

        return mapper.map(loginDTO, LoginResponse.class);
    }
}
