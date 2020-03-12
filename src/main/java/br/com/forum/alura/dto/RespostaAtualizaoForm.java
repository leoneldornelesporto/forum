package br.com.forum.alura.dto;

import br.com.forum.alura.model.Resposta;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RespostaAtualizaoForm {
    @NotEmpty
    @NotNull
    private Long respostaId;
    @NotEmpty
    @NotNull
    private String mensagem;

    public Long getRespostaId() {
        return respostaId;
    }

    public void setRespostaId(Long respostaId) {
        this.respostaId = respostaId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
