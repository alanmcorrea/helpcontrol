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
import es.indra.helpcontrol.dao.filter.ProcedimentoFilter;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.service.ProcedimentoService;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaProcedimentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProcedimentoService procedimentoService;

	private ProcedimentoFilter filtro;
	private List<Procedimento> procedimentosFiltrados;
	private Procedimento procedimentoSelecionado;
	private Long procedimento_alt_id;

	public PesquisaProcedimentoController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.procedimento_alt_id = (Long) session.getAttribute("procedimento_alt_id");
        
        if(this.procedimento_alt_id != null){
    		
    		Procedimento procedimento = procedimentoService.buscarPorID(this.procedimento_alt_id);
    		
    		procedimentosFiltrados = new ArrayList<>();
    		procedimentosFiltrados.add(procedimento);
        }
	}
 
	public void limpar() {
		filtro = new ProcedimentoFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		
		try{
			procedimentoService.excluir(procedimentoSelecionado);
			
			procedimentosFiltrados.remove(procedimentoSelecionado);
			
			FacesUtil.addInfoMessage("Procedimento " + procedimentoSelecionado.getNome() + " excluído com sucesso.");
		
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois o procedimento está associado a algum chamado.");
		}
	}

	public void pesquisar() throws DAOException {
		
			procedimentosFiltrados = procedimentoService.filtrados(filtro);
			limpar();
	}

	public List<Procedimento> getProcedimentosFiltrados() {
		return procedimentosFiltrados;
	}

	public ProcedimentoFilter getFiltro() {
		return filtro;
	}

	public Procedimento getProcedimentoSelecionado() {
		return procedimentoSelecionado;
	}

	public void setProcedimentoSelecionado(Procedimento procedimentoSelecionado) {
		this.procedimentoSelecionado = procedimentoSelecionado;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("procedimentoSelecionado", procedimentoSelecionado);
	}
}
