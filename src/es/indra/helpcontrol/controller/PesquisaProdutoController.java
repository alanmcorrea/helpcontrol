package es.indra.helpcontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.ProdutoFilter;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.service.ProdutoService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaProdutoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProdutoService produtoService;

	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	private Produto produtoSelecionado;
	private Long produto_alt_id;

	public PesquisaProdutoController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.produto_alt_id = (Long) session.getAttribute("produto_alt_id");
        
        if(this.produto_alt_id != null){
    		
    		Produto produto = produtoService.buscarPorID(this.produto_alt_id);
    		
    		produtosFiltrados = new ArrayList<>();
    		produtosFiltrados.add(produto);
        }
	}
 
	public void limpar() {
		filtro = new ProdutoFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		try{
			produtoService.excluir(produtoSelecionado);
			
			produtosFiltrados.remove(produtoSelecionado);
			
			FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getNome() + " excluído com sucesso.");
		
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois o produto está associado a algum chamado.");
		}
	}

	public void pesquisar() {
		
			produtosFiltrados = produtoService.filtrados(filtro);
			limpar();
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("produtoSelecionado", produtoSelecionado);
	}
}
