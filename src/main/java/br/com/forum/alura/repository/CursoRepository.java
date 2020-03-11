package br.com.forum.alura.repository;

import br.com.forum.alura.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso,Long> {
    Curso findByNome(String nomeCurso);
}
