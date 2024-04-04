package com.mvp.crudmicroservice.user.web.mapper;

import com.mvp.crudmicroservice.user.web.Mappable;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.web.dto.user.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

}
