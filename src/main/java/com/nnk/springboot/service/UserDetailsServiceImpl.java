package com.nnk.springboot.service;


import com.nnk.springboot.domain.JwtUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author froidefond
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method pour obtenir les détails de l'utilisateur
     *
     * @param userName le userName reçu du front
     * @return un profil s'il existe sinon leve un exception
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String userName){
        User profil = userRepository.findByUsername(userName);
        if (profil != null) {
            return JwtUserDetails.create(profil);
        } else {
            throw new UsernameNotFoundException(userName);
        }
    }

}
