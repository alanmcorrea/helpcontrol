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
import es.indra.helpcontrol.dao.filter.RegionalFilter;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.service.RegionalService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaRegionalController implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Autowired
	private RegionalService regionalService;

	private RegionalFilter filtro;
	private List<Regional> regionaisFiltradas;
	private Regional regionalSelecionada;
	private Long regional_alt_id;

	public PesquisaRegionalController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.regional_alt_id = (Long) session.getAttribute("regional_alt_id");
        
        if(this.regional_alt_id != null){
    		
    		Regional regional = regionalService.buscarPorID(this.regional_alt_id);
    		
    		regionaisFiltradas = new ArrayList<>();
    		regionaisFiltradas.add(regional);
    		
    		this.regional_alt_id = null;
    	}
	}
 
	public void limpar() {
		filtro = new RegionalFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		
		try{
			regionalService.excluir(regionalSelecionada);
			
			regionaisFiltradas.remove(regionalSelecionada);
			
			FacesUtil.addInfoMessage("Regional " + regionalSelecionada.getNome() + " excluída com sucesso.");
	
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois a regional está associada a algum chamado.");
		}
	}

	public void pesquisar() {

			regionaisFiltradas = regionalService.filtrados(filtro);
			limpar();
	}

	public List<Regional> getRegionaisFiltradas() {
		return regionaisFiltradas;
	}

	public RegionalFilter getFiltro() {
		return filtro;
	}

	public Regional getRegionalSelecionada() {
		return regionalSelecionada;
	}

	public void setRegionalSelecionada(Regional regionalSelecionada) {
		this.regionalSelecionada = regionalSelecionada;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("regionalSelecionada", regionalSelecionada);
	}

	public Long getRegional_alt_id() {
		return regional_alt_id;
	}

	public void setRegional_alt_id(Long regional_alt_id) {
		this.regional_alt_id = regional_alt_id;
	}

}
