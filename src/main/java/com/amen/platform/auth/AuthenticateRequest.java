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
    private String email; //kenet private
    String password;
    //public boolean forgetPassword = true; // Default value is false //kenet private
}
