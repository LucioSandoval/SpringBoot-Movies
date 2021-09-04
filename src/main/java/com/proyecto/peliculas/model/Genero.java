package com.proyecto.peliculas.model;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long idGenero;

    private String nombre;
    private String imagen;

    @OneToMany(mappedBy = "idGenero",fetch = FetchType.EAGER)
    @JsonIgnore
     Set<Pelicula> listaPeliculas;

    public Genero() {
    }

    public Genero(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Set<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(Set<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
}
