package com.api.disney.service;

import com.api.disney.DTO.PersonajeDTO;
import com.api.disney.exception.ResourceNotFoundException;
import com.api.disney.model.Pelicula;
import com.api.disney.model.Personaje;
import com.api.disney.repository.PersonajeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonajeServiceImp implements PersonajeService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PersonajeRepository personajeRepository;

    @Override
    public List<PersonajeDTO> listarPersonajes() {
        List<Personaje> personajes = personajeRepository.findAll();

        List<PersonajeDTO> personajeDTOS = personajes.stream().map(personaje -> mapearDTO(personaje)).collect(Collectors.toList());

        return personajeDTOS;

    }

    @Override
    public List<Personaje> listarPersonajesDetalles() {
        return personajeRepository.findAll();
    }

    @Override
    public Personaje crearPersonaje(Personaje personaje) {

        return personajeRepository.save(personaje);
    }

    @Override
    public Personaje modificarPersonaje(Personaje personaje, Long id) {

        Personaje personajeObtenido = personajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("personaje","id", id));

        personajeObtenido.setImagen(personaje.getImagen());
        personajeObtenido.setNombre(personaje.getNombre());
        personajeObtenido.setEdad(personaje.getEdad());
        personajeObtenido.setPeso(personaje.getPeso());
        personajeObtenido.setHistoria(personaje.getHistoria());

        Personaje personajeActualizado = personajeRepository.save(personajeObtenido);

        return personajeActualizado;

    }

    @Override
    public void eliminarPersonaje(Long id) {

        Personaje personaje = personajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("personaje","id",id));

        personajeRepository.delete(personaje);

    }

    @Override
    public List<Personaje> obtenerPersonajePorNombre(String nombre) {
        return personajeRepository.findByNombre(nombre);
    }

    @Override
    public List<Personaje> obtenerPersonajePorEdad(Integer edad) {
        return personajeRepository.findByEdad(edad);
    }

    @Override
    public List<Personaje> obtenerPersonajePorPeliculaID(Long id) {
        List<Personaje> personajes = personajeRepository.findAll();

        List<Personaje> personajesResponse = new ArrayList<>();
        for (Personaje personaje: personajes) {
            for (Pelicula pelicula: personaje.getPeliculas()) {
                if(pelicula.getId().equals(id)){
                    personajesResponse.add(personaje);
                }
            }
        }

        return personajesResponse;
    }


    //Convertir a DTO
    private PersonajeDTO mapearDTO(Personaje personaje){

        PersonajeDTO personajeDTO = modelMapper.map(personaje, PersonajeDTO.class);

        return personajeDTO;
    }

    //Convertir a Entidad
    private Personaje mapearEntidad(PersonajeDTO publicacionDTO){

        Personaje personaje = modelMapper.map(publicacionDTO, Personaje.class);

        return personaje;
    }
}
