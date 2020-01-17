package es.indra.helpcontrol.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.service.EquipeService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroEquipeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EquipeService equipeService;

	private Equipe equipe;

	public CadastroEquipeController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
	}

	private void limpar() {
		
		this.equipe = new Equipe();
	}

	public String salvar() throws ServiceException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}
		
		equipeService.salvar(this.equipe);

		FacesUtil.addInfoMessage("Equipe salva com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("equipe_alt_id", this.equipe.getId());
			
			//return "/equipes/PesquisaEquipe.xhtml?equipe_alt_id=" + this.equipe.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/equipes/PesquisaEquipe";
			
		}
		
		limpar();
		
		return null;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	public boolean isEditando(){
		return this.equipe.getId() != null;
	}
}
