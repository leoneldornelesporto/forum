package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.RespostaAtualizaoForm;
import br.com.forum.alura.dto.RespostaDto;
import br.com.forum.alura.dto.RespostaForm;
import br.com.forum.alura.model.Resposta;
import br.com.forum.alura.model.Topico;
import br.com.forum.alura.repository.RespostaRepository;
import br.com.forum.alura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/resposta")
public class RespostaEndPoint {
    private RespostaRepository respostaRepository;
    private TopicoRepository topicoRepository;

    @Autowired
    public RespostaEndPoint(RespostaRepository respostaRepository, TopicoRepository topicoRepository) {
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody RespostaForm respostaForm){
        Optional<Topico> repository = topicoRepository.findById(respostaForm.getTopicoId());

        if(repository.isPresent()){
            Resposta resposta = new Resposta(respostaForm.getMensagem(),repository.get());
            respostaRepository.save(resposta);
            return ResponseEntity.created(
                    MvcUriComponentsBuilder.fromMethodName(
                            RespostaEndPoint.class,"getRespostaById",resposta.getId()
                    ).build().toUri()
            ).body(new RespostaDto(resposta));
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody RespostaAtualizaoForm respostaAtualizaoForm, @PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()){
            Resposta resposta = new Resposta(respostaAtualizaoForm.getMensagem(),topico.get());
            resposta.setId(respostaAtualizaoForm.getRespostaId());
            respostaRepository.save(resposta);

            return new ResponseEntity<>(respostaAtualizaoForm,HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (respostaRepository.findById(id).isPresent()){
            respostaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllRespostas(){
        if(!respostaRepository.findAll().isEmpty()){
            return new ResponseEntity<>(RespostaDto.converter(respostaRepository.findAll()),HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRespostaById(@PathVariable @Valid @NotNull Long id){
        Optional<Resposta> resposta = respostaRepository.findById(id);
        return new ResponseEntity<>(new RespostaDto(resposta.get()),HttpStatus.OK);
    }
}
