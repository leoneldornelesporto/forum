package br.com.forum.alura.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
public class Perfil extends AbstractEntity implements GrantedAuthority {
    private static  final long serialVersionUID = 1L;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }
}
