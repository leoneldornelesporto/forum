package br.com.forum.alura.endPoint;

import br.com.forum.alura.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CursoEndPoint {
    private CursoRepository cursoRepository;

    @Autowired
    public CursoEndPoint(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @GetMapping("/cursos")
    public ResponseEntity<?> getAllCursos(){
        return new ResponseEntity<>(cursoRepository.findAll(), HttpStatus.OK);
    }
}
