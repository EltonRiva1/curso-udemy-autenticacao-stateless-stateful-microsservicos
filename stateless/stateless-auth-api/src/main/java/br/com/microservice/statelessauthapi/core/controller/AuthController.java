package br.com.microservice.statelessauthapi.core.controller;

import br.com.microservice.statelessauthapi.core.dto.AuthRequest;
import br.com.microservice.statelessauthapi.core.dto.TokenDTO;
import br.com.microservice.statelessauthapi.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest authRequest) {
        return this.authService.login(authRequest);
    }

    @PostMapping("token/validate")
    public TokenDTO validateToken(@RequestHeader String accessToken) {
        return this.authService.validateToken(accessToken);
    }
}
