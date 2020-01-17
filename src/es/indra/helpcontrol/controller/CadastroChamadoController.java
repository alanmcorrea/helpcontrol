package es.indra.helpcontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.AtualizacaoProcedimento;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Chamado;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.model.StatusChamado;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.security.Seguranca;
import es.indra.helpcontrol.service.CategoriaService;
import es.indra.helpcontrol.service.ChamadoService;
import es.indra.helpcontrol.service.EquipeService;
import es.indra.helpcontrol.service.ProcedimentoService;
import es.indra.helpcontrol.service.ProdutoService;
import es.indra.helpcontrol.service.RegionalService;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroChamadoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ChamadoService chamadoService;
	
	@Autowired
	private RegionalService regionalService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProcedimentoService procedimentoService;
	
	@Autowired
	Seguranca seguranca;

	private Chamado chamado;
	
	private boolean jaPasseiAqui = false;
	public boolean atualizacaoNao = true;	
	
	private List<Equipe> equipesAnalista;
	private List<Regional> regionais;
	private List<Usuario> usuarios;
	private List<Categoria> categorias;
	private List<Produto> produtos;
	private List<Procedimento> procedimentos;

	public CadastroChamadoController() {
		limpar();
	}
	
	public void inicializar() throws DAOException {

		this.categorias = categoriaService.buscarTodos();
		this.regionais = regionalService.buscarTodos();
		
		this.usuarios = usuarioService.buscarAnalistasAtivos();
		
		if(isEditando() && !this.jaPasseiAqui){
			this.jaPasseiAqui = true;
			carregarProdutos();
		}
		
	}
	
	public void carregarProdutos() {
		
		this.produtos = new ArrayList<>();
		this.produtos = produtoService.buscarProdutoDe(this.chamado.getCategoria());
	}
	
	public void carregarEquipes() {
		
		this.equipesAnalista = new ArrayList<>();
		this.equipesAnalista = this.chamado.getUsuario().getEquipes();
	}
	
	public void carregarProcedimentos() throws DAOException {
		
		if(this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.SIM)){
			
			this.procedimentos = new ArrayList<>();
			this.procedimentos = procedimentoService.buscarTodos();
			this.procedimentos.remove(procedimentoService.buscarPorID(1L));
		}
	}
	
	public void habilitarCampoDescAtualizacao() throws DAOException{
		
		if(this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.SIM)){
			
			this.carregarProcedimentos();
			
			this.atualizacaoNao = false;
		}
		if(this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.NAO)){
			
			this.atualizacaoNao = true;
		}
	}
	
	private void limpar() {
		
		this.chamado = new Chamado();
		this.categorias = new ArrayList<>();
		this.produtos = new ArrayList<>();
		this.regionais = new ArrayList<>();
		this.usuarios = new ArrayList<>();
		this.equipesAnalista = new ArrayList<>();
		this.procedimentos = new ArrayList<>();
	}

	public void salvar() throws ServiceException, DAOException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}	
		
		if(this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.SIM) 
				&& this.chamado.getProcedimento() == null){
		
			FacesUtil.addErrorMessage("Necessário selecionar um procedimento.");
		}
		
		if(this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.SIM) 
				&& this.chamado.getDescricaoAtualizacaoProcedimento() == ""){
		
			FacesUtil.addErrorMessage("Necessário descrever o que foi atualizado no campo 'Desc. da Atualização'.");
		}
		
		else{
			if(!this.chamado.getAtualizacaoProcedimento().equals(AtualizacaoProcedimento.SIM)){
				
				this.chamado.setProcedimento(procedimentoService.buscarPorID(1L));
				this.chamado.setDescricaoAtualizacaoProcedimento("Nao se aplica");
	
			}
			chamadoService.salvar(this.chamado);
	
			FacesUtil.addInfoMessage("Chamado salvo com sucesso!");
					
			if (editando){
						
				HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
						
				HttpSession session = request.getSession(true);  
	
				session.setAttribute("chamado_alt_id", this.chamado.getId());
						
				limpar();						
			}
					
			limpar();
		}
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}
	
	public boolean isEditando(){
		return this.chamado.getId() != null;
	}

	public List<Regional> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<Regional> regionais) {
		this.regionais = regionais;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public StatusChamado[] getStatuses(){
        
        return StatusChamado.values();
    }
	
	public AtualizacaoProcedimento[] getAtualizacaoProcedimentos(){
        
        return AtualizacaoProcedimento.values();
    }

	public List<Equipe> getEquipesAnalista() {
		return equipesAnalista;
	}

	public void setEquipesAnalista(List<Equipe> equipesAnalista) {
		this.equipesAnalista = equipesAnalista;
	}

	public boolean isAtualizacaoProcedimentoSim() {
		return atualizacaoNao;
	}	
}
