package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.RespostaDto;
import br.com.forum.alura.dto.RespostaForm;
import br.com.forum.alura.model.Resposta;
import br.com.forum.alura.model.Topico;
import br.com.forum.alura.repository.RespostaRepository;
import br.com.forum.alura.repository.TopicoRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRespostaById(@PathVariable @Valid @NotNull Long id){
        Optional<Resposta> resposta = respostaRepository.findById(id);
        return new ResponseEntity<>(new RespostaDto(resposta.get()),HttpStatus.OK);
    }
}
