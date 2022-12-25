package com.api.disney.service;

import com.api.disney.DTO.PeliculaDTO;
import com.api.disney.exception.ResourceNotFoundException;
import com.api.disney.model.Genero;
import com.api.disney.model.Pelicula;
import com.api.disney.repository.GeneroRepository;
import com.api.disney.repository.PeliculaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImp implements PeliculaService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public List<PeliculaDTO> listarPeliculas() {

        List<Pelicula> peliculas = peliculaRepository.findAll();

        List<PeliculaDTO> peliculaDTOS = peliculas.stream().map(pelicula -> mapearDTO(pelicula)).collect(Collectors.toList());

        return peliculaDTOS;
    }

    @Override
    public List<Pelicula> listarPeliculasDetalles() {
        return peliculaRepository.findAll();
    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Override
    public Pelicula editarPelicula(Pelicula pelicula, Long id) {

        Pelicula peliculaObtenida = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pelicula","id",id));

        peliculaObtenida.setImagen(pelicula.getImagen());
        peliculaObtenida.setTitulo(pelicula.getTitulo());
        peliculaObtenida.setFechaDeCreacion(pelicula.getFechaDeCreacion());
        peliculaObtenida.setCalificacion(pelicula.getCalificacion());


        Pelicula peliculaActualizada = peliculaRepository.save(peliculaObtenida);

        return peliculaActualizada;

    }

    @Override
    public void eliminarPelicula(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pelicula","id",id));

        peliculaRepository.delete(pelicula);
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorGeneroId(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genero","id",id));

        List<Pelicula> peliculas = genero.getPeliculas();

        return peliculas;
    }

    @Override
    public List<Pelicula> ordenarPeliculas(String order) {
        List<Pelicula> listaOrdenada = peliculaRepository.findAll();

        if (order.equalsIgnoreCase("asc")) {

            listaOrdenada = peliculaRepository.findAll(Sort.by(Sort.Direction.ASC,"fechaDeCreacion"));

        } else if (order.equalsIgnoreCase("desc")) {

            listaOrdenada = peliculaRepository.findAll(Sort.by(Sort.Direction.DESC,"fechaDeCreacion"));
        }

        return listaOrdenada;

    }


    //Convertir a DTO
    private PeliculaDTO mapearDTO(Pelicula pelicula){

        PeliculaDTO peliculaDTO = modelMapper.map(pelicula, PeliculaDTO.class);

        return peliculaDTO;
    }

    //Convertir a Entidad
    private Pelicula mapearEntidad(PeliculaDTO peliculaDTO){

        Pelicula pelicula = modelMapper.map(peliculaDTO, Pelicula.class);

        return pelicula;
    }
}
