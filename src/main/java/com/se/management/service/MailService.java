package com.se.management.service;


import com.se.management.model.Mail;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;


public interface MailService {

    void sendEmailVerification(String emailVerificationUrl, String to)
            throws IOException, TemplateException, MessagingException;

    /**
     * Setting the mail parameters.Send the reset link to the respective user's mail
     */
    void sendResetLink(String resetPasswordLink, String to)
            throws IOException, TemplateException, MessagingException;

    /**
     * Send an email to the user indicating an account change event with the correct
     * status
     */
    void sendAccountChangeEmail(String action, String actionStatus, String to)
            throws IOException, TemplateException, MessagingException;

    /**
     * Sends a simple mail as a MIME Multipart message
     */
    void send(Mail mail) throws MessagingException;
}
