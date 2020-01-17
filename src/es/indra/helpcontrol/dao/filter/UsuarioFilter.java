package es.indra.helpcontrol.dao.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.model.Status;
import es.indra.helpcontrol.model.StatusChamado;

public class UsuarioFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String chave;
	private String nome;
	private Status[] statuses;
	private String funcaoSupervisor;
	private String funcaoAdministrador;
	private List<Equipe> equipes = new ArrayList<>();
	private List<Grupo> grupos = new ArrayList<>();
	
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Status[] getStatuses() {
		return statuses;
	}
	public String getFuncaoSupervisor() {
		return funcaoSupervisor;
	}
	public void setFuncaoSupervisor(String funcaoSupervisor) {
		this.funcaoSupervisor = funcaoSupervisor;
	}
	public String getFuncaoAdministrador() {
		return funcaoAdministrador;
	}
	public void setFuncaoAdministrador(String funcaoAdministrador) {
		this.funcaoAdministrador = funcaoAdministrador;
	}
	public void setStatuses(Status[] statuses) {
		this.statuses = statuses;
	}
	public List<Equipe> getEquipes() {
		return equipes;
	}
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
