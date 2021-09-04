package com.proyecto.peliculas.controllers;

import com.proyecto.peliculas.model.Genero;
import com.proyecto.peliculas.model.Pelicula;
import com.proyecto.peliculas.model.Personaje;
import com.proyecto.peliculas.repositories.RepositoryGenero;
import com.proyecto.peliculas.repositories.RepositoryPelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController

public class PeliculaController {
    @Autowired
    private RepositoryPelicula repositoryPelicula;
    @Autowired
    private RepositoryGenero repositoryGenero;

    // CRUD Pelicula

    @RequestMapping(value = "/movies/crearPelicula", params = {"idg"})
    @PostMapping
    public Pelicula createPelicula (@RequestBody Pelicula pelicula, @RequestParam ("idg") Long idg){

      Genero genero=repositoryGenero.findById(idg).get();

        return repositoryPelicula.save(
            new Pelicula(pelicula.getImagen(),
                         pelicula.getTitulo(),
                         pelicula.getCalificacion(),
                         genero)
                                 );
    }

    // Metodo para editar Pelicula
    @RequestMapping(value = "/movies/editarPelicula/{id}")
    @PutMapping
    public String editarPelicula(@RequestBody Pelicula pelicula, @PathVariable("id") long id) {

        Optional<Pelicula> peliculaModificar = repositoryPelicula.findById(id);
        Pelicula peliculaAntigua =  peliculaModificar.get();
        peliculaAntigua.setTitulo(pelicula.getTitulo());
        peliculaAntigua.setImagen(pelicula.getImagen());
        peliculaAntigua.setFechaCreacion(pelicula.getFechaCreacion());
        peliculaAntigua.setCalificacion(pelicula.getCalificacion());


        repositoryPelicula.save(peliculaAntigua);
        return "Pelicula Editada";
    }

    @RequestMapping(value = "/movies/eliminarPelicula/{id}")
    @DeleteMapping
    public String eliminarPelicula(@PathVariable("id") long id){
         repositoryPelicula.deleteById(id);
         return "Pelicula Eliminada";
    }//Fin de CRUD de Pelicula






    // Metodo para buscar por nombre
    @RequestMapping(value = "/movies", params = {"name"})
    @GetMapping
    public List<Pelicula>peliculaPorNombre(@RequestParam("name") String name){
        return repositoryPelicula.findBytitulo(name);
    }



    //Método para buscar por genero
    @RequestMapping(value = "/movies", params = {"genre"})
    @GetMapping
    public List<Pelicula> peliculaPorIdGenero(@RequestParam ("genre") Long genre){

        List<Pelicula> listaPeliculas=repositoryPelicula.findAll();
        return listaPeliculas.stream().filter(pelicula -> pelicula.getIdGenero().getIdGenero()==genre).collect(Collectors.toList());
    }


   @RequestMapping(value = "/movies")
   @GetMapping
    public List<Map<String, Object>> listaPeliculas(){
    List<Pelicula> listadoPeliculas = repositoryPelicula.findAll();
       Map<String,String> namesMap = new LinkedHashMap<>();

       return listadoPeliculas.stream().map(indice ->recorrerPeliculas(indice)).collect(Collectors.toList());
   }
   public Map<String, Object> recorrerPeliculas(Pelicula pelicula){
       Map<String, Object> namesMap = new LinkedHashMap<>();
       namesMap.put("imagen", pelicula.getImagen());
       namesMap.put("titulo", pelicula.getTitulo());
       namesMap.put("fecha", pelicula.getFechaCreacion().toString());
       return namesMap;
   }

   //=========================
    @RequestMapping(value = "/movies/detallePelicula")
    @GetMapping
    public List<Map<String, Object>> detallePeliculas(){
        List<Pelicula> listapeliculas = repositoryPelicula.findAll();
        return listapeliculas.stream().map(pelicula -> mapaPelicula(pelicula) ).collect(Collectors.toList());
    }

    public Map<String, Object> mapaPelicula(Pelicula pelicula){
       Map<String, Object> mapPeliculas = new LinkedHashMap<>();
       mapPeliculas.put("imagen",pelicula.getImagen());
       mapPeliculas.put("titulo", pelicula.getTitulo());
       mapPeliculas.put("fecha",pelicula.getFechaCreacion());
       mapPeliculas.put("calificacion",pelicula.getCalificacion());
       mapPeliculas.put("Personajes",pelicula.getPeliculaPersonaje().stream().map(peliculaPersonaje -> peliculaPersonaje.getIdPersonaje().getName()).collect(Collectors.toList()));
       return mapPeliculas;
    }
}
