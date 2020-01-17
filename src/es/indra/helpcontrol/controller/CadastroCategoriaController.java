package es.indra.helpcontrol.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.service.CategoriaService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroCategoriaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CategoriaService categoriaService;

	private Categoria categoria;

	public CadastroCategoriaController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
	}

	private void limpar() {
		
		this.categoria = new Categoria();
	}

	public String salvar() throws ServiceException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}
		
		categoriaService.salvar(this.categoria);

		FacesUtil.addInfoMessage("Categoria salva com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("categoria_alt_id", this.categoria.getId());
			
			//return "/categorias/PesquisaCategoria.xhtml?categoria_alt_id=" + this.categoria.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/categorias/PesquisaCategoria";
			
		}
		
		limpar();
		
		return null;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public boolean isEditando(){
		return this.categoria.getId() != null;
	}
}
