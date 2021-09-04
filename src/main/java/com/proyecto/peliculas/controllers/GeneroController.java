package com.proyecto.peliculas.controllers;

import com.proyecto.peliculas.model.Genero;
import org.springframework.http.ResponseEntity;
import com.proyecto.peliculas.repositories.RepositoryGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class GeneroController {
    @Autowired
    private RepositoryGenero repositoryGenero;

    @RequestMapping("/generos")

    public List<Genero> getGeneros(){
       List<Genero> g=repositoryGenero.findAll();
        return g;
    }
}
