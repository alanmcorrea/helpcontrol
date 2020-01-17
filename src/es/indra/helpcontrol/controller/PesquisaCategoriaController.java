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
import es.indra.helpcontrol.dao.filter.CategoriaFilter;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.service.CategoriaService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaCategoriaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CategoriaService categoriaService;

	private CategoriaFilter filtro;
	private List<Categoria> categoriasFiltradas;
	private Categoria categoriaSelecionada;
	private Long categoria_alt_id;
	

	public PesquisaCategoriaController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.categoria_alt_id = (Long) session.getAttribute("categoria_alt_id");
        
        if(this.categoria_alt_id != null){
    		
    		Categoria categoria = categoriaService.buscarPorID(this.categoria_alt_id);
    		
    		categoriasFiltradas = new ArrayList<>();
    		categoriasFiltradas.add(categoria);
        }
	}
 
	public void limpar() {
		filtro = new CategoriaFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		try{
			categoriaService.excluir(categoriaSelecionada);
			
			categoriasFiltradas.remove(categoriaSelecionada);
			
			FacesUtil.addInfoMessage("Categoria " + categoriaSelecionada.getNome() + " excluída com sucesso.");
		
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois a categoria está associada a algum produto.");
		}
	}

	public void pesquisar() {
		
			categoriasFiltradas = categoriaService.filtrados(filtro);
			limpar();
	}

	public List<Categoria> getCategoriasFiltradas() {
		return categoriasFiltradas;
	}

	public CategoriaFilter getFiltro() {
		return filtro;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("categoriaSelecionada", categoriaSelecionada);
	}
}
