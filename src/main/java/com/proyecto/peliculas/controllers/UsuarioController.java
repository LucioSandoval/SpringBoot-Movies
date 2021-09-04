package com.proyecto.peliculas.controllers;

import com.proyecto.peliculas.model.Usuario;
import com.proyecto.peliculas.repositories.RepositoryUser;
import com.proyecto.peliculas.util.Util;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RepositoryUser repositoryUser;
    @RequestMapping(path = "/auth/register")
    @PostMapping
    public ResponseEntity <Object> registrarse(@RequestBody Usuario usuario) throws IOException {

        if(usuario.getUsername().isEmpty()|| usuario.getName().isEmpty()|| usuario.getApellido().isEmpty() || usuario.getPassword().isEmpty()){
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.FORBIDDEN);

        }

        Usuario InputUsername  = repositoryUser.findByUsername(usuario.getUsername());

        if (InputUsername!=null){
            return new ResponseEntity<>("Ya existe un usuario con ese UserName", HttpStatus.FORBIDDEN);
        }

        Usuario usuarioNuevo= new Usuario(usuario.getName(), usuario.getApellido(), usuario.getUsername(), passwordEncoder.encode(usuario.getPassword()));

        repositoryUser.save(usuarioNuevo);
        this.sendEmail(usuario.getUsername());


        return new ResponseEntity<>("Usuario creado: "+ usuario.getUsername(),HttpStatus.CREATED);



    }
    private Util util;

    @RequestMapping(path = "/auth/login")
    @PostMapping
    public ResponseEntity <?> login(Authentication authentication){
        Usuario InputUsername  = repositoryUser.findByUsername(authentication.getName());
        if(util.isGuest(authentication)){
            return new ResponseEntity<>("No esta logeado", HttpStatus.FORBIDDEN);

        }

        if (InputUsername==null){
            return new ResponseEntity<>("Usuario no existe, debe registrarse!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Usuario logeado "+InputUsername, HttpStatus.CREATED);


    }

    private void sendEmail(String destinatario) throws IOException {

        Email from = new Email("lucio.scaceres@gmail.com");
        String subject = "Bienvenido a AppMovies!";

        Email to = new Email(destinatario);
        Content content = new Content("text/plain", "Disfruta de las mejores películas..");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
