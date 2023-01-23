package com.api.disney.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegistroDTO {

    private String username;

    private String email;

    private String password;

}
