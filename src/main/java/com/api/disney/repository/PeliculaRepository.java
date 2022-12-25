package com.api.disney.repository;

import com.api.disney.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {

    public List<Pelicula> findByTitulo(String Titulo);

}
