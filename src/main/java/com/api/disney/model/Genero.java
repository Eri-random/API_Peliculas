package com.api.disney.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Genero")
@Data
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    private String nombre;

    private String imagen;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name = "generos_peliculas",
            joinColumns = @JoinColumn(name = "genero_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "id"))
    private List<Pelicula> peliculas = new ArrayList<>();
}
