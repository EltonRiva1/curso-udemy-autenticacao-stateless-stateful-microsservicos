package br.com.microservice.statefulauthapi.core.controller;

import br.com.microservice.statefulauthapi.core.dto.AuthRequest;
import br.com.microservice.statefulauthapi.core.dto.AuthUserResponse;
import br.com.microservice.statefulauthapi.core.dto.TokenDTO;
import br.com.microservice.statefulauthapi.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PostMapping("logout")
    public HashMap<String, Object> logout(@RequestHeader String accessToken) {
        this.authService.logout(accessToken);
        var response = new HashMap<String, Object>();
        response.put("status", HttpStatus.OK.name());
        response.put("code", HttpStatus.OK.value());
        return response;
    }

    @GetMapping("user")
    public AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken) {
        return this.authService.getAuthenticatedUser(accessToken);
    }
}
