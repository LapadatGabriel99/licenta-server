package com.proiect.licenta.converter;

import com.proiect.licenta.dto.UserDTO;
import com.proiect.licenta.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConverter {

    @Autowired
    private ModelMapper mapper;

    public UserDTO modelToDto(User user) {

        return mapper.map(user, UserDTO.class);
    }

    public User dtoToModel(UserDTO userDTO) {

        return mapper.map(userDTO, User.class);
    }
}
