package com.hc.medicdex.service;

import com.hc.medicdex.dto.UserEntityDto;
import com.hc.medicdex.entity.UserEntity;
import com.hc.medicdex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    public void createUser(UserEntityDto userEntityDto){
        UserEntity user = UserEntity.builder()
                        .hospitalId(userEntityDto.getHospitalId())
                        .email(userEntityDto.getEmail())
                        .password(passwordEncoder.encode(userEntityDto.getPassword()))
                        .authority("admin")
                        .phone(userEntityDto.getPhone())
                        .firstName(userEntityDto.getFirstName())
                        .lastName(userEntityDto.getLastName())
                        .address(userEntityDto.getAddress())
                        .build();

        userRepository.save(user);
        log.info("User is saved");
    }
}
