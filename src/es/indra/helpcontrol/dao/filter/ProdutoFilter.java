package es.indra.helpcontrol.dao.filter;

import java.io.Serializable;

import es.indra.helpcontrol.model.Categoria;

public class ProdutoFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	private Categoria categoria;

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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
