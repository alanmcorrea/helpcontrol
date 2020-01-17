package es.indra.helpcontrol.dao.filter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.indra.helpcontrol.model.AtualizacaoProcedimento;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.model.StatusChamado;

public class ChamadoFilter implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String incidente;
	private Date data;
    private Date dataDe;
	private Date dataAte;
	private List<Equipe> equipes;
	private Equipe equipe;
	private Regional regional;
	private Produto produto;
	private Categoria categoria;
	private String nomeRegional;
	private String local;
	private String nomeUsuario;
	private String nomeProduto;
	private Procedimento procedimento;
	private String nomeCategoria;
	private StatusChamado[] statuses;
    private String motivoRepasse;
	private String resolucao;
    private AtualizacaoProcedimento[] atualizacaoProcedimentos;
    
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getDataDe() {
		return dataDe;
	}
	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}
	public Date getDataAte() {
		return dataAte;
	}
	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}
	
	public List<Equipe> getEquipes() {
		return equipes;
	}
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	public Regional getRegional() {
		return regional;
	}
	public void setRegional(Regional regional) {
		this.regional = regional;
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
	public String getNomeRegional() {
		return nomeRegional;
	}
	public void setNomeRegional(String nomeRegional) {
		this.nomeRegional = nomeRegional;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public Procedimento getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public StatusChamado[] getStatuses() {
		return statuses;
	}
	public void setStatuses(StatusChamado[] statuses) {
		this.statuses = statuses;
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
	public AtualizacaoProcedimento[] getAtualizacaoProcedimentos() {
		return atualizacaoProcedimentos;
	}
	public void setAtualizacaoProcedimentos(AtualizacaoProcedimento[] atualizacaoProcedimentos) {
		this.atualizacaoProcedimentos = atualizacaoProcedimentos;
	}
}
