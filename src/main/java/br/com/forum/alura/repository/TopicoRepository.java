package br.com.forum.alura.repository;

import br.com.forum.alura.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    List<Topico> findByCursoNome(String nomeCurso);
    Topico findByTitulo(String titulo);
}
