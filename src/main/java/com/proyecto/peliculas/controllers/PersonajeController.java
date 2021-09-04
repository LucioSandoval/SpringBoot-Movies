package com.proyecto.peliculas.controllers;

import com.proyecto.peliculas.model.Pelicula;
import com.proyecto.peliculas.model.PeliculaPersonaje;
import com.proyecto.peliculas.model.Personaje;
import com.proyecto.peliculas.model.Usuario;
import com.proyecto.peliculas.repositories.RepositoryPelicula;
import com.proyecto.peliculas.repositories.RepositoryPeliculaPersonaje;
import com.proyecto.peliculas.repositories.RepositoryPersonaje;
import com.proyecto.peliculas.repositories.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PersonajeController {
    @Autowired
    private RepositoryPersonaje repositoryPersonaje;

    @Autowired
    private RepositoryPelicula repositoryPelicula;

    @Autowired
    private RepositoryPeliculaPersonaje repositoryPeliculaPersonaje;

    @Autowired
    private RepositoryUser repositoryUser;

    //Inicio de crud Personaje

    // Medoto para crear pelicula
    @RequestMapping(value = "/characters/createPersonaje")
    @PostMapping
    public Personaje createPersonaje(@RequestBody Personaje personaje){

        return repositoryPersonaje.save(personaje);

    }


    @RequestMapping(value = "/characters/editPersonaje/{id}")
    @PutMapping
    public String editarPersonaje (@RequestBody Personaje personaje,@PathVariable("id") long id){

        Optional<Personaje> personajemod=repositoryPersonaje.findById(id);

       if (personajemod.isPresent()){
           Personaje personajeantiguo= personajemod.get();
           personajeantiguo.setName(personaje.getName());
           personajeantiguo.setImagen(personaje.getImagen());
           personajeantiguo.setAge(personaje.getAge());
           personajeantiguo.setPeso(personaje.getPeso());
           personajeantiguo.setHistoria(personaje.getHistoria());

           repositoryPersonaje.save(personajeantiguo);
       }
        return "personaje modificado";
    }
    @RequestMapping(value = "/characters/deletePersonaje/{id}")
    @DeleteMapping
    public String deletePersonaje (@PathVariable("id") long id){

        repositoryPersonaje.deleteById(id);
        return "Personaje eliminado";
    }
    //Fin de crud Personaje

    // Busqueda de personaje por nombre
    @RequestMapping(value = "/characters", params = {"name"})
    @GetMapping
    public List<Personaje> mostrarPersonaje(@RequestParam ("name") String name){
        //System.out.println(name);
        //System.out.println(repositoryPersonaje.findByName(name));
        return repositoryPersonaje.findByName(name);
    }


    @RequestMapping(value = "/characters" , params = {"movies"})
    @GetMapping
    public Optional<Personaje> personajeById(@RequestParam ("movies") Long movies){
        return repositoryPersonaje.findById(movies);
    }

    // Busqueda de personaje por edad
    @RequestMapping(value = "/characters",params = {"age"})
    @GetMapping
    public List<Personaje> mostrarPersonaje(@RequestParam ("age") int age){
        System.out.println(age);
       // System.out.println(repositoryPersonaje.findByAge(age));
        return repositoryPersonaje.findByAge(age);
    }

    @RequestMapping(value = "/characters")
    @GetMapping
    public List<Map<String,Object>> mostrarPersonaje(){

        List<Personaje> listaPersonajes=repositoryPersonaje.findAll();

        return  listaPersonajes.stream().map(indice -> listadoPersonajes(indice)).collect(Collectors.toList());

    }
    public Map<String,Object> listadoPersonajes(Personaje personaje){
        Map<String, Object> namesMap = new LinkedHashMap<>();
        namesMap.put("imagenPersonaje",personaje.getImagen());
        namesMap.put("namePersonaje",personaje.getName());

        return namesMap;
    }
    //==============================================



    @RequestMapping(value = "/characters/list")
    @GetMapping
    public List<Map<String,Object>>listaPersonajes(){
        List<Personaje> listaPersonajess= repositoryPersonaje.findAll();

        return listaPersonajess.stream().map(indice->detallePersonaje(indice)).collect(Collectors.toList());
    }



    public Map<String,Object> detallePersonaje(Personaje personaje){
        List<Personaje> listadoPersonajes=repositoryPersonaje.findAll();
        Map<String,Object> mapa= new LinkedHashMap<>();
        mapa.put("imagenPersonaje",personaje.getImagen());
        mapa.put("namePersonaje",personaje.getName());
        mapa.put("age",personaje.getAge());
        mapa.put("peso",personaje.getPeso());
        mapa.put("historia",personaje.getHistoria());
       // mapa.put("Pelicula",repositoryPelicula.findAll().stream().flatMap(pelicula -> pelicula.getPeliculaPersonaje().stream().map(peliculaPersonaje -> peliculaPersonaje
        //        .getIdPelicula().getTitulo())).collect(Collectors.toList()));
        mapa.put("Pelicula",personaje.getPeliculaPersonaje().stream().map(peliculaPersonaje -> peliculaPersonaje.getIdPelicula().getTitulo()).collect(Collectors.toList()));

        return mapa;
    }








}
