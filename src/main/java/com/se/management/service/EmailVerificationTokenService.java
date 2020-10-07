package com.se.management.service;

import com.se.management.domain.EmailVerificationToken;
import com.se.management.domain.User;
import java.util.Optional;

public interface EmailVerificationTokenService {


    /**
     * Create an email verification token and persist it in the database which will be
     * verified by the user
     */
    void createVerificationToken(User user, String token);

    /**
     * Updates an existing token in the database with a new expiration
     */
    EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken);


    /**
     * Finds an email verification token by the @NaturalId token
     */
    Optional<EmailVerificationToken> findByToken(String token);

    /**
     * Saves an email verification token in the repository
     */
    EmailVerificationToken save(EmailVerificationToken emailVerificationToken);

    /**
     * Generates a new random UUID to be used as the token for email verification
     */
    String generateNewToken();

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    void verifyExpiration(EmailVerificationToken token);
}
