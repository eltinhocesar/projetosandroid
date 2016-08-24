package br.edu.unibratec.projetoeltondois.entity;

import java.io.Serializable;

public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	public long id;
	public String nome;
	public String logomarca;
	public String endereco;
	public String data;
	public String detalhes;
	public boolean favorito;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogomarca() {
		return logomarca;
	}

	public void setLogomarca(String logomarca) {
		this.logomarca = logomarca;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	@Override
	public String toString() {
		return nome;
	}
}
