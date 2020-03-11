package br.com.forum.alura.repository;

import br.com.forum.alura.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
}
