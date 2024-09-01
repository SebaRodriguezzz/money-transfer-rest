package moneytransfer.web.mapper;

import moneytransfer.web.dto.UserDTO;
import moneytransfer.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toUserDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<UserEntity> userEntities);
}
