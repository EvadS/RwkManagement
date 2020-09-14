package com.se.management.service;

import com.se.management.domain.RoleEntity;
import com.se.management.domain.UserEntity;
import com.se.management.repository.RoleEntityRepository;
import com.se.management.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * save user
     * @param userEntity
     * @return
     */
    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return userEntityRepository.save(userEntity);
    }

    /**
     * сначала достаю пользователя по логину а потом проверяю совпадает ли пароль в базе с паролем, который пришел на вход метода.
     * @param login
     * @return
     */
    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}