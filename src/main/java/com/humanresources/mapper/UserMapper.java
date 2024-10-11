package com.humanresources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.humanresources.dto.request.RegisterRequestDto;
import com.humanresources.dto.response.RegisterResponseDto;
import com.humanresources.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RegisterRequestDto dto);

    RegisterResponseDto userToDto(User user);
}