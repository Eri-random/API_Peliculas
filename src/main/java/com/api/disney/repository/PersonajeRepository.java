package com.api.disney.repository;

import com.api.disney.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    public List<Personaje> findByNombre(String nombre);
    public List<Personaje> findByEdad(Integer edad);


}
