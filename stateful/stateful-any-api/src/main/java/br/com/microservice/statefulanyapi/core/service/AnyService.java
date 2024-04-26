package br.com.microservice.statefulanyapi.core.service;

import br.com.microservice.statefulanyapi.core.dto.AnyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnyService {
    private final TokenService tokenService;

    public AnyResponse getData(String accessToken) {
        this.tokenService.validateToken(accessToken);
        var authUser = this.tokenService.getAuthenticatedUser(accessToken);
        return new AnyResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), authUser);
    }
}
