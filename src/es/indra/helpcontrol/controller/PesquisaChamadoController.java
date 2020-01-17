package es.indra.helpcontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.ChamadoFilter;
import es.indra.helpcontrol.model.AtualizacaoProcedimento;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Chamado;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.model.StatusChamado;
import es.indra.helpcontrol.service.CategoriaService;
import es.indra.helpcontrol.service.ChamadoService;
import es.indra.helpcontrol.service.EquipeService;
import es.indra.helpcontrol.service.ProcedimentoService;
import es.indra.helpcontrol.service.RegionalService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaChamadoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ChamadoService chamadoService;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private RegionalService regionalService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProcedimentoService procedimentoService;
	
	StatusChamado[] statuses;
	AtualizacaoProcedimento[] atualizacaoProcedimentos;
	
	ChamadoFilter filtro;
	private Chamado chamadoSelecionado;
	private List<Chamado> chamadosFiltrados;
	private Long chamado_alt_id;
	private List<Equipe> equipes;
	private List<Procedimento> procedimentos;
	private List<Regional> regionais;
	private List<Categoria> categorias;
	
	PesquisaChamadoController() {
		
		filtro = new ChamadoFilter();
		chamadosFiltrados = new ArrayList<>();
	}
	
	//@PostConstruct
	public void inicializar() throws DAOException{
		
		equipes = equipeService.buscarTodos();
		regionais = regionalService.buscarTodos();
		categorias = categoriaService.buscarTodos();
		procedimentos = procedimentoService.buscarTodos();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.chamado_alt_id = (Long) session.getAttribute("chamado_alt_id");
        
        if(this.chamado_alt_id != null){
    		
    		Chamado chamado = chamadoService.buscarPorID(this.chamado_alt_id);
    		
    		chamadosFiltrados = new ArrayList<>();
    		chamadosFiltrados.add(chamado);
        }
	}
	
	public void limpar() {
		filtro = new ChamadoFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		
		chamadoService.excluir(chamadoSelecionado);
		
		chamadosFiltrados.remove(chamadoSelecionado);
		
		FacesUtil.addInfoMessage("Chamado " + chamadoSelecionado.getIncidente() + " excluído com sucesso.");
	}
	
	//Apenas caso eu vá testar a navegação sem outcome
	public String irParaPesquisa(){
		
		statuses = null;
		atualizacaoProcedimentos = null;
		
		filtro = null;
		chamadoSelecionado = null;
		chamadosFiltrados = null;
		chamado_alt_id = null;
		equipes = null;
		procedimentos = null;
		regionais = null;
		categorias = null;
		
		return "/chamados/CadastroChamado";
	}
	
	public void pesquisar(){
		
		chamadosFiltrados = chamadoService.filtrados(filtro);
	}
	
	public StatusChamado[] getStatuses(){
		
		return StatusChamado.values();
	}

	public List<Chamado> getChamadosFiltrados() {
		return chamadosFiltrados;
	}

	public ChamadoFilter getFiltro() {
		return filtro;
	}

	public Chamado getChamadoSelecionado() {
		return chamadoSelecionado;
	}

	public void setChamadoSelecionado(Chamado chamadoSelecionado) {
		this.chamadoSelecionado = chamadoSelecionado;
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public List<Regional> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<Regional> regionais) {
		this.regionais = regionais;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	public AtualizacaoProcedimento[] getAtualizacaoProcedimentos(){
		
		return AtualizacaoProcedimento.values();
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}
}