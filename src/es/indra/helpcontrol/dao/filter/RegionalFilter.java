package es.indra.helpcontrol.dao.filter;

import java.io.Serializable;

public class RegionalFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
