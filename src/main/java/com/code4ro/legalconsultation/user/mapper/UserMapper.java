package com.code4ro.legalconsultation.user.mapper;

import com.code4ro.legalconsultation.user.model.dto.UserDto;
import com.code4ro.legalconsultation.user.model.persistence.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserDto userDto);

    UserDto map(User user);

    List<UserDto> map(List<User> users);
}
