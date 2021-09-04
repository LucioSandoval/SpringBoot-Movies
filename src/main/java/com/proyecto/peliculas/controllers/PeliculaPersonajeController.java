package com.proyecto.peliculas.controllers;

import com.proyecto.peliculas.repositories.RepositoryPeliculaPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeliculaPersonajeController {
    @Autowired
    private RepositoryPeliculaPersonaje repositoryPeliculaPersonaje;
}
