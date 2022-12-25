package com.api.disney.service;

import com.api.disney.DTO.PersonajeDTO;
import com.api.disney.model.Personaje;

import java.util.List;

public interface PersonajeService {

    public List<PersonajeDTO> listarPersonajes();

    public List<Personaje> listarPersonajesDetalles();

    public Personaje crearPersonaje(Personaje personaje);

    public Personaje modificarPersonaje(Personaje personaje, Long id);

    public void eliminarPersonaje(Long id);

    public List<Personaje> obtenerPersonajePorNombre(String nombre);

    public List<Personaje> obtenerPersonajePorEdad(Integer edad);

    public List<Personaje> obtenerPersonajePorPeliculaID(Long id);
}
