package com.gustavosdaniel.authservice.authentication;

import com.gustavosdaniel.authservice.user.LoginRequestDTO;
import com.gustavosdaniel.authservice.user.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> tokenOptional = authUserService.authenticate(loginRequestDTO);

        if (tokenOptional.isEmpty()){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @Operation(summary = "Validate Token")
    @GetMapping(path = "/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authUserService.validateToken(authorizationHeader.substring(7))
                ? ResponseEntity.ok().build()  // validateToken retornar true, o endpoint responde com 200 OK
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // retornar false, ele responde com 401 Unauthorized.
    }
}
