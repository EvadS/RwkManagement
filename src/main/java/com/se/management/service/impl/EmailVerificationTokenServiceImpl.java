package com.se.management.service.impl;

import com.se.management.domain.EmailVerificationToken;
import com.se.management.domain.User;
import com.se.management.exception.InvalidTokenRequestException;
import com.se.management.model.TokenStatus;
import com.se.management.repository.EmailVerificationTokenRepository;
import com.se.management.service.EmailVerificationTokenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private static final Logger logger = Logger.getLogger(EmailVerificationTokenService.class);
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Value("${app.token.email.verification.duration}")
    private Long emailVerificationTokenExpiryDuration;


    public EmailVerificationTokenServiceImpl(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    /**
     * Create an email verification token and persist it in the database which will be
     * verified by the user
     *
     * @param user
     * @param token
     */
    @Override
    public void createVerificationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        logger.info("Generated Email verification token [" + emailVerificationToken + "]");
        emailVerificationTokenRepository.save(emailVerificationToken);

    }

    /**
     * Updates an existing token in the database with a new expiration
     *
     * @param existingToken
     */
    @Override
    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        logger.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    /**
     * Finds an email verification token by the @NaturalId token
     *
     * @param token
     */
    @Override
    public Optional<EmailVerificationToken> findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    /**
     * Saves an email verification token in the repository
     *
     * @param emailVerificationToken
     */
    @Override
    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return emailVerificationTokenRepository.save(emailVerificationToken);
    }

    /**
     * Generates a new random UUID to be used as the token for email verification
     */
    @Override
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     *
     * @param token
     */
    @Override
    public void verifyExpiration(EmailVerificationToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Email Verification Token", token.getToken(), "Expired token. Please issue a new request");
        }
    }
}
