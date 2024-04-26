package br.com.microservice.statelessanyapi.core.service;

import br.com.microservice.statelessanyapi.core.dto.AnyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnyService {
    private final JwtService jwtService;

    public AnyResponse getData(String accessToken) {
        this.jwtService.validateAccessToken(accessToken);
        var authUser = this.jwtService.getAuthenticatedUser(accessToken);
        return new AnyResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), authUser);
    }
}
