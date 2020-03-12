package br.com.forum.alura.repository;

import br.com.forum.alura.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Page<Usuario> findByNome(String nome, Pageable paginacao);
    Optional<Usuario> findByEmail(String email);
}
