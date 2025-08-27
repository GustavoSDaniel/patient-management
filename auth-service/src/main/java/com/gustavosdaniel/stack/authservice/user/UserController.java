package com.gustavosdaniel.stack.authservice.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new User")
    public ResponseEntity<CreateUserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {

        CreateUserResponseDTO createUserResponseDTO = userService.createUser(createUserRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Lista de todos usuarios")
    public ResponseEntity<Page<User>> getUsers(

            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size


    ) {

        Pageable pageable = PageRequest.of(page -1, size);


        Page<User> allUser = userService.getUsers(pageable);


        return ResponseEntity.ok(allUser);
    }

    @GetMapping(path = "/{email}")
    @Operation(summary = "Busca os usuarios através do Email")
    public ResponseEntity<User> getUserEmail(@PathVariable @Email String email) {

        Optional<User> user = userService.findByEmail(email);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(path = "/search")
    @Operation(summary = "Busca usuario por nome ")
    public ResponseEntity<Page<CreateUserResponseDTO>> searchUsersByName(
            @RequestParam String username,
            @PageableDefault(size =  20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){

        log.info("Searching users by name: {} with pagination: {}", username, pageable);

        Page<CreateUserResponseDTO> users = userService.searchUsersByName(pageable, username);

        return ResponseEntity.ok(users);

    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Atualiza apenas os campos que for necessario ")
    public ResponseEntity<UpdateUserResponseDTO> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO){

        UpdateUserResponseDTO updateUser = userService.updateUser(id, updateUserRequestDTO);

        return ResponseEntity.ok(updateUser);

    }


    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deletar usuario ")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
