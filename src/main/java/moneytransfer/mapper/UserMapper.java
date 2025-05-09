package moneytransfer.mapper;

import moneytransfer.dto.UserDTO;
import moneytransfer.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toUserDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<UserEntity> userEntities);
}
