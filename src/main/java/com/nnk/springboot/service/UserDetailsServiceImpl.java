package com.nnk.springboot.service;


import com.nnk.springboot.domain.JwtUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method pour obtenir les détails de l'utilisateur
     *
     * @param userName le mail du profil
     * @return mail du profil
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
