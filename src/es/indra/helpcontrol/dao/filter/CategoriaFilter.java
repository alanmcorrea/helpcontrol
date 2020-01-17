package es.indra.helpcontrol.dao.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.indra.helpcontrol.model.Produto;

public class CategoriaFilter implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	private List<Produto> produtos = new ArrayList<>();
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
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
