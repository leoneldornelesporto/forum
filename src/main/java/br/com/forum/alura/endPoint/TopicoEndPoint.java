package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.AtualizacaoTopicoForm;
import br.com.forum.alura.dto.TopicoDetalheDto;
import br.com.forum.alura.dto.TopicoDto;
import br.com.forum.alura.dto.TopicoForm;
import br.com.forum.alura.model.Topico;
import br.com.forum.alura.repository.CursoRepository;
import br.com.forum.alura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/topico")
public class TopicoEndPoint {
    private TopicoRepository topicoRepository;
    private CursoRepository cursoRepository;

    @Autowired
    public TopicoEndPoint(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllTopicos(){
        if(topicoRepository.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(TopicoDto.converter(topicoRepository.findAll()),HttpStatus.FOUND);
        }
    }

    @GetMapping("nome/{nomeCurso}")
    public ResponseEntity<?> getTopicoByNomeCurso(@PathVariable String nomeCurso){
        if (!topicoRepository.findByCursoNome(nomeCurso).isEmpty()){
            return new ResponseEntity<>(topicoRepository.findByCursoNome(nomeCurso),HttpStatus.FOUND);
        }
        else {
            if(nomeCurso == null){
                return new ResponseEntity<>(topicoRepository.findAll(),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalheDto> getById(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()){
            return new ResponseEntity<>(new TopicoDetalheDto(topico.get()),HttpStatus.FOUND);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> create(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder){
            Topico topico = topicoForm.converter(cursoRepository);
            topicoRepository.save(topico);

            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        if (topicoRepository.findById(id).isPresent()){
            Topico topico = form.update(id,topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (topicoRepository.findById(id).isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
