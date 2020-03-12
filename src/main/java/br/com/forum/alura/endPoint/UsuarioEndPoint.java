package br.com.forum.alura.endPoint;

import br.com.forum.alura.dto.UsuarioAtualizacaoForm;
import br.com.forum.alura.dto.UsuarioDto;
import br.com.forum.alura.model.Usuario;
import br.com.forum.alura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import javax.transaction.Transactional;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioEndPoint {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioEndPoint(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Cacheable(value = "getAllUsuarios")
    public Page<UsuarioDto> getAllUsuarios(@RequestParam (required = false) String nome, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        if(nome == null){
            return UsuarioDto.converter(usuarioRepository.findAll(paginacao));
        }
        else {
            return UsuarioDto.converter(usuarioRepository.findByNome(nome,paginacao));
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "getAllUsuarios", allEntries = true)
    public ResponseEntity<?> create(@RequestBody Usuario usuario){
        if(!(usuario == null)){
            usuarioRepository.save(usuario);
            return ResponseEntity.created(MvcUriComponentsBuilder.fromMethodName(UsuarioEndPoint.class,"create",usuario.getId()).build().toUri()).body(new UsuarioDto(usuario));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "getAllUsuarios", allEntries = true)
    public ResponseEntity<?> update(@RequestBody UsuarioAtualizacaoForm usuarioAtualizacaoForm, @PathVariable Long id){
        if(usuarioRepository.findById(id).isPresent()){
            Usuario usuario = usuarioAtualizacaoForm.formUpdate(usuarioRepository,id);
            return new ResponseEntity<>(new UsuarioDto(usuario),HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "getAllUsuarios", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (usuarioRepository.findById(id).isPresent()){
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
