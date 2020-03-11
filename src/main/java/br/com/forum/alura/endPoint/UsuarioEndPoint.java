package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.TopicoDto;
import br.com.forum.alura.model.Curso;
import br.com.forum.alura.model.Topico;
import br.com.forum.alura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioEndPoint {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioEndPoint(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsuarios(){
        if(usuarioRepository.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(usuarioRepository.findAll(),HttpStatus.OK);
        }
    }
}
