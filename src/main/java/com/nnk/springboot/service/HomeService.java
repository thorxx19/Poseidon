package com.nnk.springboot.service;

import com.nnk.springboot.domain.JwtUserDetails;
import com.nnk.springboot.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author froidefond
 */
@Slf4j
@Service
public class HomeService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Point d'entrée de l'applie
     *
     * @param user les information de l'utilisateur
     * @return des information a la vue pour affichage
     */
    public String home(Principal user) {

        // connection avec mdp et userName
        if(user instanceof UsernamePasswordAuthenticationToken){
            JwtUserDetails profil = getUsernamePasswordLoginInfo(user);
            String autority = null;
            for (GrantedAuthority auto : profil.getAuthorities()) {
                if (auto != null) {
                    autority = String.valueOf(auto);
                    break;
                }
            }
            // vérifie le role alouer a l'utilisateur
            if (autority != null && autority.equals("ROLE_ADMIN")) {
                return "redirect:/user/list";
            }
            if (autority != null && autority.equals("ROLE_USER")) {
                return "redirect:/bidList/list";
            }
        }
        // connection avec GitHub
        else if(user instanceof OAuth2AuthenticationToken){
            String role = getOauth2LoginInfo(user);
            // vérifie le role alouer a l'utilisateur
            if (role != null && role.equals("ROLE_ADMIN")) {
                return "redirect:/user/list";
            }
            if (role != null && role.equals("ROLE_USER")) {
                return "redirect:/bidList/list";
            }
        }
        return "home";
    }

    /**
     * point d'entrée pour les admin
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

    /**
     * Method pour récup les info d'un utilisateur qui se connecte avec MDP et UserName
     *
     * @param user info reçu du front
     * @return un object de type JwtUserDetails
     */
    private JwtUserDetails getUsernamePasswordLoginInfo(Principal user)
    {
        JwtUserDetails u = null;

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if(token.isAuthenticated()){
            u = (JwtUserDetails) token.getPrincipal();
        }
        else{
            return null;
        }
        return u;
    }

    /**
     * Method pour récup les info d'une personne qui se connecte avec github
     *
     * @param user info reçu du front
     * @return le role de l'utilisateur
     */
    private String getOauth2LoginInfo(Principal user){

        String roleRecup = null;
        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        Collection<GrantedAuthority> roles = authToken.getAuthorities();

        if(authToken.isAuthenticated()){

            for (GrantedAuthority role : roles) {
                String rolesVerif = String.valueOf(role);
                if (rolesVerif.equals("ROLE_ADMIN") || rolesVerif.equals("ROLE_USER")) {
                    roleRecup = rolesVerif;
                    break;
                }
            }
        }
        else{
            return null;
        }
        return roleRecup;
    }




}
