package com.gustavosdaniel.authservice.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {

        if (userRepository.existsByEmail(createUserRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User newUser = userMapper.toUser(createUserRequestDTO);

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());

        newUser.setPassword(encodedPassword);

        newUser.setRole(UserRole.USER);

        User userSalved = userRepository.save(newUser);

        log.info("User created successfully with email: {}", userSalved.getEmail());

        return userMapper.toCreateUserResponseDTO(userSalved);

    }

    @Override
    public Page<User> getUsers(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);

    }
}
