package com.amen.platform.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequest {
    private String email;
    String password;
    private boolean forgetPassword = false; // Default value is false
    public boolean isForgetPassword(boolean forgetPassword) {
        return forgetPassword;
    }
}
