package com.amen.platform.auth;

import com.amen.platform.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Indexed(unique = true)
    private String nickname;
    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
}
