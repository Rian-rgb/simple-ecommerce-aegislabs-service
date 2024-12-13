package com.aegisultimateknologi.simple_ecommerce_service.mapper;

import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToUser(UserRegisterRequest request);

}
