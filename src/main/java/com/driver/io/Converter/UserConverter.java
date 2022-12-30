package com.driver.io.Converter;

import com.driver.io.entity.UserEntity;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.UserResponse;
import com.driver.shared.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {
    public static UserEntity ConvertRequestToEntity(UserDetailsRequestModel user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static UserDto convertEntityToDto(UserEntity userEntity) {
       return UserDto.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .build();
    }
    public static UserResponse convertDtoToResponse(UserDto userDto) {
        return UserResponse.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }

}
