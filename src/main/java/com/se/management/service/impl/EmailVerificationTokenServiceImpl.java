package com.se.management.service.impl;

import com.se.management.domain.EmailVerificationToken;
import com.se.management.domain.User;
import com.se.management.service.EmailVerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {
    /**
     * Create an email verification token and persist it in the database which will be
     * verified by the user
     *
     * @param user
     * @param token
     */
    @Override
    public void createVerificationToken(User user, String token) {

    }

    /**
     * Updates an existing token in the database with a new expiration
     *
     * @param existingToken
     */
    @Override
    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        return null;
    }

    /**
     * Finds an email verification token by the @NaturalId token
     *
     * @param token
     */
    @Override
    public Optional<EmailVerificationToken> findByToken(String token) {
        return Optional.empty();
    }

    /**
     * Saves an email verification token in the repository
     *
     * @param emailVerificationToken
     */
    @Override
    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return null;
    }

    /**
     * Generates a new random UUID to be used as the token for email verification
     */
    @Override
    public String generateNewToken() {
        return null;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     *
     * @param token
     */
    @Override
    public void verifyExpiration(EmailVerificationToken token) {

    }
}
