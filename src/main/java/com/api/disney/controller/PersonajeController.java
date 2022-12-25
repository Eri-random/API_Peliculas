package com.api.disney.controller;

import com.api.disney.DTO.PersonajeDTO;
import com.api.disney.model.Personaje;
import com.api.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping
    public List<PersonajeDTO> obtenerPersonajes(){
        return personajeService.listarPersonajes();
    }

    @GetMapping("/details")
    public List<Personaje> obtenerPersonajesDetalles(){
        return personajeService.listarPersonajesDetalles();
    }

    @GetMapping(params="name")
    public List<Personaje> obtenerPersonajePorNombre(@RequestParam(name ="name") String nombre){
        return personajeService.obtenerPersonajePorNombre(nombre);
    }

    @GetMapping(params = "age")
    public List<Personaje> obtenerPersonajePorEdad(@RequestParam(name = "age") Integer edad){
        return personajeService.obtenerPersonajePorEdad(edad);
    }

    @GetMapping(params = "movies")
    public List<Personaje> obtenerPersonajePorEdad(@RequestParam(name = "movies") Long id){
        return personajeService.obtenerPersonajePorPeliculaID(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Personaje> crearPersonaje(@Valid @RequestBody Personaje personaje){
        return new ResponseEntity<>(personajeService.crearPersonaje(personaje), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizarPersonaje(@PathVariable(value = "id") Long id, @Valid @RequestBody Personaje personaje){
        Personaje personajeActualizado = personajeService.modificarPersonaje(personaje,id);
        return new ResponseEntity<>(personajeActualizado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPersonaje(@PathVariable(value = "id") Long id){
        personajeService.eliminarPersonaje(id);
        return new ResponseEntity<>("Personaje Eliminado con exito",HttpStatus.OK);
    }

}
