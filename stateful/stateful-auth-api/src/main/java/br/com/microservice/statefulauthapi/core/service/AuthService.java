package br.com.microservice.statefulauthapi.core.service;

import br.com.microservice.statefulauthapi.core.dto.AuthRequest;
import br.com.microservice.statefulauthapi.core.dto.AuthUserResponse;
import br.com.microservice.statefulauthapi.core.dto.TokenDTO;
import br.com.microservice.statefulauthapi.core.model.User;
import br.com.microservice.statefulauthapi.core.repository.UserRepository;
import br.com.microservice.statefulauthapi.infra.exception.AuthenticationException;
import br.com.microservice.statefulauthapi.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenDTO login(AuthRequest authRequest) {
        var user = this.findByUsername(authRequest.username());
        var accessToken = this.tokenService.createToken(user.getUsername());
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
        var valid = this.tokenService.validateAccessToken(accessToken);
        if (valid) {
            return new TokenDTO(accessToken);
        }
        throw new AuthenticationException("Invalid token!");
    }

    public AuthUserResponse getAuthenticatedUser(String accessToken) {
        var tokenData = this.tokenService.getTokenData(accessToken);
        var user = this.findByUsername(tokenData.username());
        return new AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String accessToken) {
        this.tokenService.deleteRedisToken(accessToken);
    }

    private User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ValidationException("User not found!"));
    }

    private void validateExistingToken(String accessToken) {
        if (isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed!");
        }
    }
}
