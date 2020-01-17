package es.indra.helpcontrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import es.indra.helpcontrol.model.Grupo;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_id_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
	private Long id;
	
	@NotNull
	@Column(nullable = false, unique = true, length = 10)
	private String chave;
	
	@NotNull
	@Column(nullable = false, length = 80)
	private String nome;
	
	@NotNull
	@Column(nullable = false, length = 256)
	private String senha;
	
	@NotNull
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;
	
	@NotNull
	@Column(name = "funcao_supervisor", nullable = false, length = 5)
	private String funcaoSupervisor;
	
	@NotNull
	@Column(name = "funcao_administrador", nullable = false, length = 5)
	private String funcaoAdministrador;

	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "usuario_equipe", 
	joinColumns = @JoinColumn(name="usuario_id"), 
	inverseJoinColumns = @JoinColumn(name = "equipe_id"))
	private List<Equipe> equipes = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "usuario_grupo", 
	joinColumns = @JoinColumn(name="usuario_id"), 
	inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos = new ArrayList<>();
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Status getStatus() {
	    return status;
	}

	public void setStatus(Status status) {
	    this.status = status;
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
	
	@Transient
	public boolean isAdministrador(){
		
		if(getId() != null && getChave() != null){
			return getChave().equals("ADMS") || getChave().equals("adms") || getId().equals(1L);
		}
		else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}