package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setNationality(userEntity.getNationality());
        userDTO.setBirthDate(userEntity.getBirthDate());
        userDTO.setBalance(userEntity.getBalance());
        return userDTO;
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setNationality(userDTO.getNationality());
        userEntity.setBirthDate(userDTO.getBirthDate());
        userEntity.setBalance(userDTO.getBalance());
        return userEntity;
    }

    public List<UserDTO> toUserDTOList(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::toUserDTO).collect(Collectors.toList());
    }
}
