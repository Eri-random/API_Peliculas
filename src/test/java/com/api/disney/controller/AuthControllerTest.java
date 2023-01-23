package com.api.disney.controller;

import static org.mockito.BDDMockito.given;

import com.api.disney.DTO.LoginDTO;
import com.api.disney.DTO.RegistroDTO;
import com.api.disney.exception.ResourceNotFoundException;
import com.api.disney.model.Rol;
import com.api.disney.model.Usuario;
import com.api.disney.repository.RolRepository;
import com.api.disney.repository.UsuarioRepository;
import com.api.disney.security.CustomUserDetailsService;
import com.api.disney.security.JwtAuthenticationEntryPoint;
import com.api.disney.security.JwtTokenProvider;
import com.api.disney.service.EmailService;
import com.api.disney.service.PeliculaService;
import com.api.disney.service.PersonajeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @MockBean
    private PeliculaService peliculaService;

    @MockBean
    private PersonajeService personajeService;



    @BeforeEach
    void testRegistrarUsuario() throws Exception {

         RegistroDTO registroDTO  = new RegistroDTO();

         registroDTO.setUsername("eri");
         registroDTO.setEmail("eriherrerait@gmail.com");
         registroDTO.setPassword("root");

         Rol rol = new Rol();
         rol.setNombre("ROLE_ADMIN");

         given(rolRepository.save(rol)).willReturn(rol);

         given(rolRepository.findByNombre("ROLE_ADMIN")).willReturn(Optional.of(rol));

        ResultActions response =  mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(registroDTO)));

        response.andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void testLoguearUsuario() throws Exception {

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setUsernameOrEmail("eri");
        loginDTO.setPassword("root");

        ResultActions response =  mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(loginDTO)));

        response.andDo(print())
                .andExpect(status().isOk());
    }
}
