package com.mvp.crudmicroservice.user.web.mapper;

import com.mvp.crudmicroservice.user.domain.user.Telegram;
import com.mvp.crudmicroservice.user.web.Mappable;
import com.mvp.crudmicroservice.user.web.dto.user.TelegramDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelegramMapper extends Mappable<Telegram, TelegramDto> {
}
