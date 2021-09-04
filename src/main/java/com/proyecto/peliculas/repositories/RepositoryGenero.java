package com.proyecto.peliculas.repositories;

import com.proyecto.peliculas.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryGenero extends JpaRepository<Genero, Long> {
}
