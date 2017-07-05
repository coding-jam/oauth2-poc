package it.codingjam.services;

import it.codingjam.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

/**
 * Created by acomo on 26/03/17.
 */
@Named
public class UserService implements UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional
                .ofNullable(userRepository.findOne(username))
                .orElseThrow(() -> new UsernameNotFoundException(username + " no found"));
    }
}
