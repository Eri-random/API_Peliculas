package com.api.disney.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Personaje")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull(message = "La imagen no debe ser nula")
    private String imagen;

    @NotNull(message = "El nombre no debe ser nulo")
    private String nombre;

    private Integer edad;

    private Double peso;

    @NotNull(message = "La historia no debe ser nula")
    @Size(max = 250, message = "La historia no debe tener mas de 100 caracteres")
    private String historia;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name = "personajes_peliculas",
            joinColumns = @JoinColumn(name = "personaje_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "id"))
    private Set<Pelicula> peliculas = new HashSet<>();


}
