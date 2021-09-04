package com.proyecto.peliculas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Entity
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long idPelicula;

    private String imagen;
    private String titulo;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    private int calificacion;

    @OneToMany(mappedBy = "idPelicula",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PeliculaPersonaje> peliculaPersonaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idGenero")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JsonIgnore
    private Genero idGenero;



    public Pelicula() {
    }

    public Pelicula( String imagen, String titulo, int calificacion, Genero idGenero) {

        this.imagen = imagen;
        this.titulo = titulo;
        this.fechaCreacion = new Date();
        this.calificacion = calificacion;
        this.idGenero=idGenero;
    }

    public Genero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Genero idGenero) {
        this.idGenero = idGenero;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Set<PeliculaPersonaje> getPeliculaPersonaje() {
        return peliculaPersonaje;
    }

    public void setPeliculaPersonaje(Set<PeliculaPersonaje> peliculaPersonaje) {
        this.peliculaPersonaje = peliculaPersonaje;
    }


}
