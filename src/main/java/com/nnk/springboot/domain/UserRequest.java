package com.nnk.springboot.domain;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRequest {

    @NotEmpty
    String password;

    @NotEmpty
    String userName;

}
