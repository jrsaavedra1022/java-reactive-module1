package edu.co.cedesistemas.reactiva.modulo1.movies.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDetailsService().findByUsername(username).block();

        if (user == null){
            throw new UsernameNotFoundException("usuario no encontrado: " + username);
        }
        return user;
    }

    public boolean isUserValid(UserDetails userDetails, Authentication authentication){
        String storedPassword = userDetails.getPassword();
        if(userDetails.getPassword().startsWith("{bcrypt}")){
            storedPassword = storedPassword.replace("{bcrypt}", "");
        }
        return BCrypt.checkpw(authentication.getCredentials().toString(), storedPassword);
    }

}
