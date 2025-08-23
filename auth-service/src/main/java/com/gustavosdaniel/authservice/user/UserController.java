package com.gustavosdaniel.authservice.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
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
}
