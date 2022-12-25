package com.api.disney.service;

import com.api.disney.DTO.PeliculaDTO;
import com.api.disney.model.Pelicula;

import java.util.List;

public interface PeliculaService {

    public List<PeliculaDTO> listarPeliculas();

    public List<Pelicula> listarPeliculasDetalles();

    public Pelicula crearPelicula(Pelicula pelicula);

    public Pelicula editarPelicula(Pelicula pelicula, Long id);

    public void eliminarPelicula(Long id);

    public List<Pelicula> obtenerPeliculasPorTitulo(String titulo);

    public List<Pelicula>obtenerPeliculasPorGeneroId(Long id);

    public List<Pelicula>ordenarPeliculas(String order);

}
