package br.com.microservice.statelessauthapi.core.service;

import br.com.microservice.statelessauthapi.core.dto.AuthRequest;
import br.com.microservice.statelessauthapi.core.dto.TokenDTO;
import br.com.microservice.statelessauthapi.core.repository.UserRepository;
import br.com.microservice.statelessauthapi.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDTO login(AuthRequest authRequest) {
        var user = this.userRepository.findByUsername(authRequest.username()).orElseThrow(() -> new ValidationException("User not found!"));
        var accessToken = this.jwtService.createToken(user);
        this.validatePassword(authRequest.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (isEmpty(rawPassword)) {
            throw new ValidationException("The password must be informed!");
        }
        if (!this.passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is incorrect!");
        }
    }

    public TokenDTO validateToken(String accessToken) {
        this.validateExistingToken(accessToken);
        this.jwtService.validateAccessToken(accessToken);
        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if (isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed!");
        }
    }
}
