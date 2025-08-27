package com.gustavosdaniel.stack.authservice.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public Page<CreateUserResponseDTO> searchUsersByName(Pageable pageable, String username) {

        Page<User> user = userRepository.findByName(username, pageable);

        return user.map(userMapper::toCreateUserResponseDTO);

    }


    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);

    }

    @Transactional
    @Override
    public UpdateUserResponseDTO updateUser(UUID id, UpdateUserRequestDTO updateUserRequestDTO) {

        User updatedUser = userRepository.findById(id).orElseThrow(UsernameNotFoundException::new);

        if (userRepository.existsByEmailAndIdNot(updateUserRequestDTO.getEmail(), id)) {
            log.warn("Attempt to use existing email: {}", updateUserRequestDTO.getEmail());
            throw new EmailAlreadyExistsException();
        }

        // 3. Atualiza apenas os campos que não são nulos no DTO
        if (updateUserRequestDTO.getUsername() != null) {

            updatedUser.setUsername(updateUserRequestDTO.getUsername());
        }

        if (updateUserRequestDTO.getEmail() != null) {

            updatedUser.setEmail(updateUserRequestDTO.getEmail());
        }

        if (updateUserRequestDTO.getPassword() != null
                && !updateUserRequestDTO.getPassword().isBlank()) { // isblank verifica se uma string está vazia ou contém apenas espaços em branco.

            updatedUser.setPassword(passwordEncoder.encode(updateUserRequestDTO.getPassword()));
        }

        User savedUser = userRepository.save(updatedUser);

        return userMapper.toUpdateUserResponseDTO(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {

        User user = userRepository.findById(id).orElseThrow(UsernameNotFoundException::new);

        userRepository.delete(user);
    }
}



