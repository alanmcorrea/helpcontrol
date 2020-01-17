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
import es.indra.helpcontrol.dao.filter.UsuarioFilter;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.model.Status;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaSupervisorController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;

	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;
	private Usuario usuarioSelecionado;
	private Long usuario_alt_id;

	public PesquisaSupervisorController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.usuario_alt_id = (Long) session.getAttribute("usuario_alt_id");
        
        if(this.usuario_alt_id != null){
    		
    		Usuario usuario = usuarioService.buscarPorID(this.usuario_alt_id);
    		
    		usuariosFiltrados = new ArrayList<>();
    		usuariosFiltrados.add(usuario);
    	}
	}
 
	public void limpar() {
		filtro = new UsuarioFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		
		try{
			usuarioService.excluir(usuarioSelecionado);
		
			usuariosFiltrados.remove(usuarioSelecionado);
		
			FacesUtil.addInfoMessage("Supervisor " + usuarioSelecionado.getNome() + " excluído com sucesso.");
	
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois o supervisor está associado a algum chamado.");
		}
	}

	public void pesquisar() {
			
			usuariosFiltrados = usuarioService.supervisoresFiltrados(filtro);
			limpar();
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}
	
	public Status[] getStatuses(){
		
		return Status.values();
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuarioSelecionado", usuarioSelecionado);
	}
}
