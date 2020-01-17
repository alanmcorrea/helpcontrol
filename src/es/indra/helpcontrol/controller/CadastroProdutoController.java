package es.indra.helpcontrol.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.service.CategoriaService;
import es.indra.helpcontrol.service.ProdutoService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroProdutoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CategoriaService categoriaService;

	private Produto produto;
	
	private List<Categoria> categorias;

	public CadastroProdutoController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
		categorias = categoriaService.buscarTodos();
		
	}

	private void limpar() {
		
		this.produto = new Produto();
	}

	public String salvar() throws ServiceException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}
		
		produtoService.salvar(this.produto);

		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  

			session.setAttribute("produto_alt_id", this.produto.getId());
			
			//return "/produtos/PesquisaProduto.xhtml?produto_alt_id=" + this.produto.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/produtos/PesquisaProduto";
			
		}
		
		limpar();
		
		return null;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public boolean isEditando(){
		return this.produto.getId() != null;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}
