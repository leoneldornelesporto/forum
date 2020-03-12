package br.com.forum.alura.dto;

import br.com.forum.alura.model.Usuario;
import br.com.forum.alura.repository.UsuarioRepository;

public class UsuarioAtualizacaoForm {
    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario formUpdate(UsuarioRepository usuarioRepository, Long id) {
        Usuario usuario = usuarioRepository.getOne(id);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);

        return usuario;
    }
}
