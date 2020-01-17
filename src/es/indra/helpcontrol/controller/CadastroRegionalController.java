package es.indra.helpcontrol.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.service.RegionalService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroRegionalController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RegionalService regionalService;

	private Regional regional;

	public CadastroRegionalController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
	}

	private void limpar() {
		
		this.regional = new Regional();
	}

	public String salvar() throws ServiceException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}
		
		regionalService.salvar(this.regional);

		FacesUtil.addInfoMessage("Regional salva com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("regional_alt_id", this.regional.getId());
			
			//return "/regionais/PesquisaRegional.xhtml?regional_alt_id=" + this.regional.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/regionais/PesquisaRegional";
		}
		
		limpar();
		
		return null;
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}
	
	public boolean isEditando(){
		return this.regional.getId() != null;
	}
}
