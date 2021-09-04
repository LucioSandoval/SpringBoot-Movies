package com.proyecto.peliculas.repositories;

import com.proyecto.peliculas.model.PeliculaPersonaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryPeliculaPersonaje extends JpaRepository<PeliculaPersonaje, Long> {
}
