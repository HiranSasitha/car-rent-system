package springboot.poscarrent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springboot.poscarrent.model.User;
import springboot.poscarrent.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankUserNamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //check user input username password is correct
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();//get username pw by authentication

        List<User> userList = userRepository.findByEmail(userName);// find user from username
        if(userList.size()>0){
            if(passwordEncoder.matches(password,userList.get(0).getPassword())){//match  is user pw is correct

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(userList.get(0).getRole()));//set user role
                return new UsernamePasswordAuthenticationToken(userName,password,authorities);
            }else {
                throw new BadCredentialsException("wrong Password");
            }

        }else {
            throw new BadCredentialsException("wrong user name or password");
        }



    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
