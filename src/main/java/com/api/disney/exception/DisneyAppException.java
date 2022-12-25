package com.api.disney.exception;

import org.springframework.http.HttpStatus;

public class DisneyAppException extends RuntimeException{

    private static final long serialVersionUID=1L;

    private HttpStatus estado;
    private String mensaje;

    public DisneyAppException(HttpStatus estado, String mensaje){
        super();
        this.estado = estado;
        this.mensaje=mensaje;
    }

    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
