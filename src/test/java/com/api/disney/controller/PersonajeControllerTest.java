package com.api.disney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import com.api.disney.DTO.PersonajeDTO;
import com.api.disney.model.Personaje;
import com.api.disney.repository.RolRepository;
import com.api.disney.repository.UsuarioRepository;
import com.api.disney.security.CustomUserDetailsService;
import com.api.disney.security.JwtAuthenticationEntryPoint;
import com.api.disney.security.JwtAuthenticationFilter;
import com.api.disney.security.JwtTokenProvider;
import com.api.disney.service.EmailService;
import com.api.disney.service.PeliculaService;
import com.api.disney.service.PersonajeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonajeService personajeService;

    @Mock
    private ModelMapper modelMapper;

    @MockBean
    private PeliculaService peliculaService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    JwtTokenProvider jwtUtil;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void testObtenerPersonajes() throws Exception {

        List<Personaje> listaPersonajes = new ArrayList<>();

        listaPersonajes.add(Personaje.builder().nombre("Mirabel").imagen("imagen").historia("Mirabel vive en un pueblo encantado").build());
        listaPersonajes.add(Personaje.builder().nombre("Sam").imagen("imagen").historia("Sam es un perro rescatista").build());

        List<PersonajeDTO> personajeDTOS = listaPersonajes.stream().map(personaje -> mapearDTO(personaje)).collect(Collectors.toList());


        given(personajeService.listarPersonajes()).willReturn(personajeDTOS);

        ResultActions response = mockMvc.perform(get("/characters"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(personajeDTOS.size())));

    }

    @Test
    void testObtenerPersonajePorNombre() throws Exception {
       List<Personaje> personajes = new ArrayList<>() ;
       personajes.add(Personaje.builder().nombre("Mirabel").imagen("imagen").historia("Mirabel vive en un pueblo encantado").build());

        given(personajeService.obtenerPersonajePorNombre("Mirabel")).willReturn(personajes);

        ResultActions response = mockMvc.perform(get("/characters").param("name","Mirabel").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personajes)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(personajes.size())));

    }

    @Test
    @WithMockUser(username="eri",roles={"ADMIN"})
    void testGuardarPersonaje() throws Exception {

        Personaje personaje = Personaje.builder()
                                .nombre("Mirabel")
                                .imagen("imagen")
                                .historia("Mirabel vive en un bosque encantado")
                                .build();

        given(personajeService.crearPersonaje(any(personaje.getClass())))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaje)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(personaje.getNombre())))
                .andExpect(jsonPath("$.imagen", is(personaje.getImagen())))
                .andExpect(jsonPath("$.historia", is(personaje.getHistoria())));

    }


    private PersonajeDTO mapearDTO(Personaje personaje){

        PersonajeDTO personajeDTO = modelMapper.map(personaje, PersonajeDTO.class);

        return personajeDTO;
    }
}
