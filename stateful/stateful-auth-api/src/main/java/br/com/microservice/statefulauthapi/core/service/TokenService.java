package br.com.microservice.statefulauthapi.core.service;

import br.com.microservice.statefulauthapi.core.dto.TokenData;
import br.com.microservice.statefulauthapi.infra.exception.AuthenticationException;
import br.com.microservice.statefulauthapi.infra.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class TokenService {
    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Long ONE_DAY_IN_SECONDS = 86400L;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public String createToken(String username) {
        var accessToken = UUID.randomUUID().toString();
        var data = new TokenData(username);
        var jsonData = this.getJsonData(data);
        this.redisTemplate.opsForValue().set(accessToken, jsonData);
        this.redisTemplate.expireAt(accessToken, Instant.now().plusSeconds(ONE_DAY_IN_SECONDS));
        return accessToken;
    }

    private String getJsonData(Object payload) {
        try {
            return this.objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            return "";
        }
    }

    public TokenData getTokenData(String token) {
        var accessToken = this.extractToken(token);
        var jsonString = this.getRedisTokenValue(accessToken);
        try {
            return this.objectMapper.readValue(jsonString, TokenData.class);
        } catch (Exception e) {
            throw new AuthenticationException("Error extracting the authenticated user: " + e.getMessage());
        }
    }

    public boolean validateAccessToken(String token) {
        var accessToken = this.extractToken(token);
        var data = this.getRedisTokenValue(accessToken);
        return !isEmpty(data);
    }

    private String getRedisTokenValue(String token) {
        return this.redisTemplate.opsForValue().get(token);
    }

    public void deleteRedisToken(String token) {
        var accessToken = this.extractToken(token);
        this.redisTemplate.delete(accessToken);
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new ValidationException("The access token was not informed.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }
}
