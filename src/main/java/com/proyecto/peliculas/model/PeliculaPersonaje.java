package com.proyecto.peliculas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PeliculaPersonaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPelicula")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JsonIgnore
    private Pelicula idPelicula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personajeId")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JsonIgnore
    private Personaje idPersonaje;

    public PeliculaPersonaje() {
    }

    public PeliculaPersonaje(Pelicula idPelicula, Personaje idPersonaje) {
        this.idPelicula = idPelicula;
        this.idPersonaje = idPersonaje;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Personaje getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(Personaje idPersonaje) {
        this.idPersonaje = idPersonaje;
    }
}
