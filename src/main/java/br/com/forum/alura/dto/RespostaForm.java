package br.com.forum.alura.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RespostaForm {
    @NotEmpty
    @NotNull
    private Long topicoId;
    @NotEmpty
    @NotNull
    private String mensagem;



    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
