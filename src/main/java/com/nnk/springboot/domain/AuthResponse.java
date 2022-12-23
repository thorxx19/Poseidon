package com.nnk.springboot.domain;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AuthResponse {

    String message;
    Integer userId;
    String accessToken;
    List<?> datas;
    Object data;

}
