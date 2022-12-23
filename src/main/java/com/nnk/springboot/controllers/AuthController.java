package com.nnk.springboot.controllers;





import com.nnk.springboot.domain.AuthResponse;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserRequest;
import com.nnk.springboot.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {


    @Autowired
    private AuthService authService;


    /**
     * methode pour connecter un profil avec ces identifient
     *
     * @param loginRequest les login du profil
     * @return un token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest loginRequest){
        return authService.login(loginRequest);
    }

    /**
     * methode pour enregister un nouveau client
     *
     * @param profil un object
     * @return 201 ou 400
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody User profil)  {
        return authService.register(profil);
    }
}
