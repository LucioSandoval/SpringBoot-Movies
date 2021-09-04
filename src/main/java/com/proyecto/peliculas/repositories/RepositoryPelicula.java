package com.proyecto.peliculas.repositories;

import com.proyecto.peliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RepositoryPelicula extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findBytitulo(@Param("titulo") String titulo);
    //List<Pelicula> findByGenero(@Param("genre") String genre);
}
