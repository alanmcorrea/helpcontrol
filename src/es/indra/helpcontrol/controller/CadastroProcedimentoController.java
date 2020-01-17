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
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.service.ProcedimentoService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroProcedimentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProcedimentoService procedimentoService;

	private Procedimento procedimento;

	public CadastroProcedimentoController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
	}

	private void limpar() {
		
		this.procedimento = new Procedimento();
	}

	public String salvar() throws ServiceException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		}
		
		procedimentoService.salvar(this.procedimento);

		FacesUtil.addInfoMessage("Procedimento salvo com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("procedimento_alt_id", this.procedimento.getId());
			
			//return "/procedimentos/PesquisaProcedimento.xhtml?procedimento_alt_id=" + this.procedimento.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/procedimentos/PesquisaProcedimento";
			
		}
		
		limpar();
		
		return null;
	}

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	
	public boolean isEditando(){
		return this.procedimento.getId() != null;
	}
}
