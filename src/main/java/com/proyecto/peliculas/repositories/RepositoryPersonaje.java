package com.proyecto.peliculas.repositories;

import com.proyecto.peliculas.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RepositoryPersonaje extends JpaRepository<Personaje, Long> {

    List<Personaje> findByName(@Param("name") String name);

    List<Personaje> findByAge(@Param("age") int age);





}
