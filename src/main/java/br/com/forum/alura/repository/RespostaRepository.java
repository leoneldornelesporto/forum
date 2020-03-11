package br.com.forum.alura.repository;

import br.com.forum.alura.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta,Long> {
}
