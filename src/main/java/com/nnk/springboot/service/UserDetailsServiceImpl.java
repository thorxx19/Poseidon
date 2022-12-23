package com.nnk.springboot.service;



import com.nnk.springboot.domain.JwtUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    /**
     * Method pour ajouter l'id au token
     *
     * @param id du profil
     * @return l'id du profil
     */
    public UserDetails loadUserById(Integer id) {
        User profil = userRepository.findById(id).get();
        return JwtUserDetails.create(profil);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
