package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.*;
import br.com.forum.alura.model.Topico;
import br.com.forum.alura.repository.CursoRepository;
import br.com.forum.alura.repository.RespostaRepository;
import br.com.forum.alura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/topico")
public class TopicoEndPoint {
    private TopicoRepository topicoRepository;
    private CursoRepository cursoRepository;
    private RespostaRepository respostaRepository;

    @Autowired
    public TopicoEndPoint(TopicoRepository topicoRepository, CursoRepository cursoRepository, RespostaRepository respostaRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.respostaRepository = respostaRepository;
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

    @DeleteMapping("/resposta/{id}")
    @Transactional
    public ResponseEntity<?> deleteResposta(@PathVariable Long id){
        if(respostaRepository.findById(id).isPresent()){
            respostaRepository.deleteById(id);
            return new ResponseEntity<>(id,HttpStatus.OK);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
