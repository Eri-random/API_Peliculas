package com.api.disney.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "Pelicula")
@Data
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    private String imagen;

    @NotNull
    private String titulo;

    @NotNull
    private Date fechaDeCreacion;

    @Min(1)
    @Max(5)
    private Integer calificacion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name = "personajes_peliculas",
            joinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id", referencedColumnName = "id"))
    private Set<Personaje> personajes = new HashSet<>();



}
