package io.datajek.moneytransferrest.service.mapper;

import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toUserDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<UserEntity> userEntities);
}
