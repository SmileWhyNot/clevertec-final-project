package ru.clevertec.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.config.TokenConfig;
import ru.clevertec.banking.entity.Role;
import ru.clevertec.banking.repository.RefreshTokenRepository;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService extends AbstractTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenConfig refreshConfig;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, TokenConfig refreshConfig) {
        super(refreshConfig);
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshConfig = refreshConfig;
    }

    @Transactional
    public void updateRefreshToken(String refreshToken) {
        final Long id = extractId(refreshToken);
        refreshTokenRepository.updateRefreshToken(id, refreshToken);
    }

    public boolean isRefreshTokenValid(String providedRefreshToken) {
        final Long id = extractId(providedRefreshToken);
        return refreshTokenRepository.isRefreshTokenValid(id, providedRefreshToken);
    }

    public boolean isRefreshTokenNotExpired(String refreshToken) {
        return extractExpiration(refreshToken).before(new Date());
    }

    public String generateRefreshToken(Long userId, List<Role> authorities) {
        return generateToken(Collections.singletonMap("authorities", authorities),
                             userId, refreshConfig.getRefreshTokenExpirationMs());
    }
}
