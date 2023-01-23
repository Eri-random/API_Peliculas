package com.api.disney.service;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.api.disney.DTO.PersonajeDTO;
import com.api.disney.exception.ResourceNotFoundException;
import com.api.disney.model.Pelicula;
import com.api.disney.model.Personaje;
import com.api.disney.repository.PersonajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;


import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PersonajeServiceTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeServiceImp personajeService;

    private Personaje personaje;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){

        Set<Pelicula> peliculas = new HashSet<>();

        Pelicula pelicula = new Pelicula();
        pelicula.setId(1L);
        pelicula.setImagen("imagen");
        pelicula.setFechaDeCreacion(new Date("15/10/2022"));
        pelicula.setTitulo("Encanto");

        peliculas.add(pelicula);


        personaje = personaje.builder()
                .id(1L)
                .imagen("imagen")
                .nombre("Mirabel")
                .historia("Mirabel vive en un casa magica que se mueve sola")
                .peliculas(peliculas)
                .build();
    }

    @Test
    void testGuardarPersonaje(){

        given(personajeRepository.save(personaje)).willReturn(personaje);

        Personaje personajeGuardado = personajeService.crearPersonaje(personaje);

        assertThat(personajeGuardado).isNotNull();
    }

    @Test
    void testListarPersonaje(){
        Personaje personaje1 = Personaje.builder()
                .imagen("imagen")
                .nombre("Sam")
                .historia("Sam es un perro rescatista en la playa de California")
                .build();

        given(personajeRepository.findAll()).willReturn(List.of(personaje, personaje1));

        modelMapper = new ModelMapper();

        List<PersonajeDTO> personajeDTOS = personajeService.listarPersonajes();

        assertThat(personajeDTOS).isNotNull();
        assertThat(personajeDTOS.size()).isEqualTo(2);
    }

    @Test
    void testActualizarPersonaje(){

        given(personajeRepository.save(personaje)).willReturn(personaje);

        Personaje personajeGuardado = personajeService.crearPersonaje(personaje);


        personajeGuardado.setNombre("Miranda");

        assertThrows(ResourceNotFoundException.class,() ->{
            personajeService.modificarPersonaje(personajeGuardado,personaje.getId());
        });


        assertThat(personajeGuardado.getNombre()).isEqualTo("Miranda");
        assertThat(personaje.getNombre()).isEqualTo("Miranda");


    }

    @Test
    void testObtenerPersonajePorNombre(){

        given(personajeRepository.findByNombre("Mirabel")).willReturn(List.of(personaje));

        Personaje personajeObtenido = personajeService.obtenerPersonajePorNombre(personaje.getNombre()).get(0);

        assertThat(personajeObtenido.getNombre()).isEqualTo("Mirabel");
    }

    @Test
    void testObtenerPersonajePorPeliculaId(){

        Set<Pelicula> peliculas2 = new HashSet<>();

        Pelicula pelicula2 = new Pelicula();
        pelicula2.setId(2L);
        pelicula2.setImagen("imagen");
        pelicula2.setFechaDeCreacion(new Date("15/09/2022"));
        pelicula2.setTitulo("Avatar");

        peliculas2.add(pelicula2);


        Personaje personaje1 = Personaje.builder()
                .imagen("imagen")
                .nombre("Sam")
                .historia("Sam es un perro rescatista en la playa de California")
                .peliculas(peliculas2)
                .build();

        given(personajeRepository.findAll()).willReturn(List.of(personaje,personaje1));

        List<Personaje> personajes = personajeService.obtenerPersonajePorPeliculaID(2L);

        assertThat(personajes.size()).isEqualTo(1);
        assertThat(personajes.get(0).getNombre()).isEqualTo("Sam");


    }



}
