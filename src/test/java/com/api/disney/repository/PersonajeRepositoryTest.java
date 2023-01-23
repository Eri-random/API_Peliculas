package com.api.disney.repository;

import com.api.disney.model.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    private Personaje personaje;

    @BeforeEach
    void setUp(){
        personaje = personaje.builder()
                .imagen("imagen")
                .nombre("Mirabel")
                .historia("Mirabel vive en un casa magica que se mueve sola")
                .build();
    }

    @Test
    void testGuardarPersonaje(){
        //given
        Personaje personaje1 = Personaje.builder()
                .imagen("imagen")
                .nombre("Sam")
                .historia("Sam es un perro rescatista en la playa de California")
                .build();
        //when
        Personaje personajeGuardado = personajeRepository.save(personaje1);

        //then
        assertThat(personaje1).isNotNull();
        assertThat(personajeGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testListarPersonajes(){
        //given
        Personaje personaje1 = Personaje.builder()
                .imagen("imagen")
                .nombre("Sam")
                .historia("Sam es un perro rescatista en la playa de California")
                .build();

        personajeRepository.save(personaje);
        personajeRepository.save(personaje1);

        List<Personaje> personajeList = personajeRepository.findAll();

        assertThat(personajeList).isNotNull();
        assertThat(personajeList.size()).isEqualTo(2);

    }

    @Test
    void testObtenerPersonajePorId(){
        personajeRepository.save(personaje);

        Personaje personajeObtenido = personajeRepository.findById(personaje.getId()).get();

        assertThat(personajeObtenido).isNotNull();
        assertThat(personajeObtenido.getId()).isEqualTo(1);
    }

    @Test
    void testActualizarPersonaje(){
        personajeRepository.save(personaje);

        Personaje personajeDB = personajeRepository.findById(personaje.getId()).get();

        personajeDB.setNombre("Miranda");

        Personaje personajeActualizado = personajeRepository.save(personajeDB);

        assertThat(personajeDB.getNombre()).isEqualTo("Miranda");
    }

    @Test
    void testEliminarPersonaje(){
        personajeRepository.save(personaje);

        personajeRepository.deleteById(personaje.getId());
        Optional<Personaje> empleadoOptional = personajeRepository.findById(personaje.getId());

        assertThat(empleadoOptional).isEmpty();
    }

}
