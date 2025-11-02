package com.ibrahimsolak.studentloginapp.security.manager;

import com.ibrahimsolak.studentloginapp.user.entity.User;
import com.ibrahimsolak.studentloginapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager  implements AuthenticationManager {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getUserByUsername(authentication.getName());

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            throw new BadCredentialsException("You provided an incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    }
}
