package com.driver.service.impl;

import com.driver.io.Converter.UserConverter;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDetailsRequestModel user) throws  AlreadyExistsException {
        UserEntity userExist=userRepository.findByUserId(user.getUserId());
        if(userExist!=null)
            throw new AlreadyExistsException("user  already exists...!!");

        UserEntity userEntity = UserConverter.ConvertRequestToEntity(user);
        userEntity=userRepository.save(userEntity);

        return UserConverter.convertEntityToDto(userEntity);

    }


    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(email);
        return UserConverter.convertEntityToDto(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        return UserConverter.convertEntityToDto(userEntity);
    }

    @Override
    public UserDto updateUser(String userId, UserDetailsRequestModel userDetails) throws Exception {
        long id= userRepository.findByUserId(userId).getId();
       UserEntity user= UserEntity.builder()
                .id(id)
               .userId(userDetails.getUserId())
               .firstName(userDetails.getFirstName())
               .lastName(userDetails.getLastName())
               .email(userDetails.getEmail())
                .build();
      user=userRepository.save(user);
        return UserConverter.convertEntityToDto(user);
    }

    @Override
    public OperationStatusModel deleteUser(String userId) throws Exception {
        UserEntity user=userRepository.findByUserId(userId);
        OperationStatusModel operationStatusModel;
        if(user==null)
        {

            operationStatusModel= OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.ERROR.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        else{
            long id=user.getId();
            userRepository.deleteById(id);
            operationStatusModel= OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        return  operationStatusModel;


    }

    @Override
    public List<UserDto> getUsers() {

        List<UserDto> userList = new ArrayList<>();
        List<UserEntity> userlist;
        userlist = userRepository.findAll();

        for (UserEntity user : userlist) {
            UserDto userDto = UserConverter.convertEntityToDto(user);
            userList.add(userDto);
        }
        return userList;
    }

}