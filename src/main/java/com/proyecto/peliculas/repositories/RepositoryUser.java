package com.proyecto.peliculas.repositories;

import com.proyecto.peliculas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


    @RepositoryRestResource
    public interface RepositoryUser extends  JpaRepository<Usuario, Long> {
        Usuario findByUsername(@Param("username") String username);
    }
