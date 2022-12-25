package com.api.disney.DTO;

import lombok.Data;

@Data
public class LoginDTO {

    private String usernameOrEmail;

    private String password;

}
