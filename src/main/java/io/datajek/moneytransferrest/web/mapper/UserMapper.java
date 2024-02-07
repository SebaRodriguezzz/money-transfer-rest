package io.datajek.moneytransferrest.web.mapper;

import io.datajek.moneytransferrest.web.dto.UserDTO;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toUserDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<UserEntity> userEntities);
}
