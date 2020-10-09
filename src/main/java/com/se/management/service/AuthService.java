package com.se.management.service;

import com.se.management.domain.EmailVerificationToken;
import com.se.management.domain.PasswordResetToken;
import com.se.management.domain.RefreshToken;
import com.se.management.domain.User;
import com.se.management.model.CustomUserDetails;
import com.se.management.model.request.*;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {
    Optional<User> registerUser(RegistrationRequest newRegistrationRequest);

    Boolean emailAlreadyExists(String email);

    /**
     * Checks if the given email already exists in the database repository or not
     *
     * @return true if the email exists else false
     */
    Boolean usernameAlreadyExists(String username);

    /**
     * Authenticate user and log them in given a loginRequest
     */
    Optional<Authentication> authenticateUser(LoginRequest loginRequest);

    /**
     * Confirms the user verification based on the token expiry and mark the user as active.
     * If user is already verified, save the unnecessary database calls.
     */
    Optional<User> confirmEmailRegistration(String emailToken);

    /**
     * Attempt to regenerate a new email verification token given a valid
     * previous expired token. If the previous token is valid, increase its expiry
     * else update the token value and add a new expiration.
     */
    Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken);
    /**
     * Validates the password of the current logged in user with the given password
     */

    /**
     * Updates the password of the current logged in user
     */
    Optional<User> updatePassword(CustomUserDetails customUserDetails,
                                  UpdatePasswordRequest updatePasswordRequest);

    /**
//     * Generates a JWT token for the validated client
//     */
//    String generateToken(CustomUserDetails customUserDetails);

    /**
     * Creates and persists the refresh token for the user device. If device exists
     * already, we don't care. Unused devices with expired tokens should be cleaned
     * with a cron job. The generated token would be encapsulated within the jwt.
     * Remove the existing refresh token as the old one should not remain valid.
     */
    Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest);

    /**
     * Refresh the expired jwt token using a refresh token and device info. The
     * * refresh token is mapped to a specific device and if it is unexpired, can help
     * * generate a new jwt. If the refresh token is inactive for a device or it is expired,
     * * throw appropriate errors.
     */
    Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest);

    /**
     * Generates a password reset token from the given reset request
     */
    Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest);

    /**
     * Reset a password given a reset request and return the updated user
     */
    Optional<User> resetPassword(PasswordResetRequest passwordResetRequest);
}
