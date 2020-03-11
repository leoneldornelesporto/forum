package br.com.forum.alura.dto;

import br.com.forum.alura.model.Resposta;
import br.com.forum.alura.model.StatusTopico;
import br.com.forum.alura.model.Topico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDetalheDto extends TopicoDto{
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respostas;

    public TopicoDetalheDto(Topico topico) {
        super(topico);
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();
        this.respostas = new ArrayList<>();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }
}