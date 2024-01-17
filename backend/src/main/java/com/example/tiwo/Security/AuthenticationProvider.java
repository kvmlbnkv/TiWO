package com.example.tiwo.Security;

import com.example.tiwo.Entities.UserEntity;
import com.example.tiwo.Exceptions.NoSuchUserException;
import com.example.tiwo.Exceptions.UnsuccessfulLoginException;
import com.example.tiwo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserEntity> user = userRepository.getByUsername(authentication.getName());
        if(user.isPresent()){
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword())){
                System.out.println(authentication.getCredentials().toString());
                System.out.println(user.get().getPassword());
                throw new BadCredentialsException("Niepoprawne dane logowania");
            }
            return new UsernamePasswordAuthenticationToken(authentication.getName(), user.get().getPassword(), null);
        }
        else {
            throw(new NoSuchUserException());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
