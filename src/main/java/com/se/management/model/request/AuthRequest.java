package com.se.management.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}