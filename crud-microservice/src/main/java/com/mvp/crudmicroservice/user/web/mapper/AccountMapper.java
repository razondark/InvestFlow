package com.mvp.crudmicroservice.user.web.mapper;

import com.mvp.crudmicroservice.user.web.Mappable;
import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.web.dto.user.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountMapper extends Mappable<Account, AccountDto> {

    @Mappings({
            @Mapping(target = "userDto", source = "user"),
    })
    AccountDto toDto(Account entity);

    @Mappings({
            @Mapping(target = "user", source = "userDto"),
    })
    Account toEntity(AccountDto dto);
}
