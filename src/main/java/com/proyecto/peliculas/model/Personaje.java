package com.proyecto.peliculas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long idPersonaje;
    private String imagen;
    private String name;
    private int age;
    private double peso;
    private String historia;

    @OneToMany(mappedBy = "idPersonaje",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PeliculaPersonaje> peliculaPersonaje;

    public Personaje() {
    }

    public Personaje(String imagen, String name, int age, double peso, String historia) {
        this.imagen = imagen;
        this.name = name;
        this.age = age;
        this.peso = peso;
        this.historia = historia;
    }

    public Long getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(Long idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Set<PeliculaPersonaje> getPeliculaPersonaje() {
        return peliculaPersonaje;
    }

    public void setPeliculaPersonaje(Set<PeliculaPersonaje> peliculaPersonaje) {
        this.peliculaPersonaje = peliculaPersonaje;
    }


}
