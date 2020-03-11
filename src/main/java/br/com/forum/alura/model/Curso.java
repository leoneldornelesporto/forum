package br.com.forum.alura.model;

import javax.persistence.Entity;

@Entity
public class Curso extends AbstractEntity{

	private String nome;
	private String categoria;

	public Curso(String nome, String categoria) {
		this.nome = nome;
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
