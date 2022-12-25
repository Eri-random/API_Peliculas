package com.api.disney.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PeliculaDTO {

    private String imagen;

    private String titulo;

    private Date fechaDeCreacion;
}
