package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Length(min = 1, max = 125)
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Length(min = 12, max = 125)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Le mot de passe doit contenir 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial pour une longueur minimum de 12 caractères.")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Length(min = 1, max = 125)
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Length(min = 1, max = 125)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
