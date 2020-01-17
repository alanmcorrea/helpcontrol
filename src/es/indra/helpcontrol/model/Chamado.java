package es.indra.helpcontrol.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "chamado")
public class Chamado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "chamado_sequence", sequenceName = "chamado_id_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chamado_sequence")
	private Long id;
	
	@NotNull
	@Column(nullable = false, length = 20)
	private String incidente;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "equipe_id")
	private Equipe equipe;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date data;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "regional_id")
	private Regional regional;
	
	@NotNull
	@Column(nullable = false, length = 40)
	private String local;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "procedimento_id")
	private Procedimento procedimento;
	
	@Column(name = "descricao_atualizacao_procedimento", length = 1000)
    private String descricaoAtualizacaoProcedimento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
    private StatusChamado status;
	
	@NotNull
	@Column(name = "motivo_do_repasse", length = 1000)
    private String motivoRepasse;
	
	@NotNull
	@Column(length = 1000)
	private String resolucao;
	
	@NotNull
    @Enumerated(EnumType.STRING)
	@Column(name="atualizacao_procedimento", nullable = false, length = 20)
    private AtualizacaoProcedimento atualizacaoProcedimento;

	//Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIncidente() {
		return incidente;
	}

	public void setIncidente(String incidente) {
		this.incidente = incidente;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	
	public String getDescricaoAtualizacaoProcedimento() {
		return descricaoAtualizacaoProcedimento;
	}

	public void setDescricaoAtualizacaoProcedimento(String descricaoAtualizacaoProcedimento) {
		this.descricaoAtualizacaoProcedimento = descricaoAtualizacaoProcedimento;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public String getMotivoRepasse() {
		return motivoRepasse;
	}

	public void setMotivoRepasse(String motivoRepasse) {
		this.motivoRepasse = motivoRepasse;
	}

	public String getResolucao() {
		return resolucao;
	}

	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}

	public AtualizacaoProcedimento getAtualizacaoProcedimento() {
		return atualizacaoProcedimento;
	}

	public void setAtualizacaoProcedimento(AtualizacaoProcedimento atualizacaoProcedimento) {
		this.atualizacaoProcedimento = atualizacaoProcedimento;
	}

	@Transient
	public boolean isNovo(){
		
		return getId() == null;
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
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
//	@Transient
//	public boolean isAtualizacaoNao(){
//		
//		return getAtualizacaoProcedimento() == AtualizacaoProcedimento.NAO;
//	}

}
