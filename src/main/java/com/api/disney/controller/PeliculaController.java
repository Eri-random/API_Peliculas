package com.api.disney.controller;

import com.api.disney.DTO.PeliculaDTO;
import com.api.disney.model.Pelicula;
import com.api.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<PeliculaDTO> listarPeliculas(){

        return peliculaService.listarPeliculas();
    }

    @GetMapping("/details")
    public List<Pelicula> listarPeliculasDetalles(){
        return peliculaService.listarPeliculasDetalles();
    }

    @GetMapping(params = "name")
    public List<Pelicula> listarPeliculaPorTitulo(@RequestParam(name = "name") String titulo){
        return peliculaService.obtenerPeliculasPorTitulo(titulo);
    }

    @GetMapping(params = "genre")
    public List<Pelicula>listarPeliculaPorGeneroId(@RequestParam(name = "genre") Long id){
        return peliculaService.obtenerPeliculasPorGeneroId(id);
    }

    @GetMapping( params = "order")
    public List<Pelicula>ordenarPeliculas(@RequestParam(name = "order") String order){
        return peliculaService.ordenarPeliculas(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@Valid @RequestBody Pelicula pelicula){

        return new ResponseEntity<>(peliculaService.crearPelicula(pelicula), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> editarPelicula(@Valid @RequestBody Pelicula pelicula, @PathVariable(value = "id") Long id){
        Pelicula peliculaActualizada = peliculaService.editarPelicula(pelicula,id);
        return new ResponseEntity<>(peliculaActualizada, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPelicula(@PathVariable(value = "id") Long id){
        peliculaService.eliminarPelicula(id);
        return new ResponseEntity<>("Pelicula Eliminada con exito",HttpStatus.OK);
    }


}
