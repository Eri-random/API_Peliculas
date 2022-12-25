package com.api.disney.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String nombre;
}
