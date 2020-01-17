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
import es.indra.helpcontrol.dao.filter.EquipeFilter;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.service.EquipeService;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class PesquisaEquipeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private UsuarioService usuarioService;

	private EquipeFilter filtro;
	private List<Equipe> equipesFiltradas;
	private Equipe equipeSelecionada;
	private Long equipe_alt_id;
	
	private List<Usuario> analistasEquipe;

	public PesquisaEquipeController() {
		limpar();
	}
	
	@PostConstruct
	public void inicializar() throws DAOException{
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();  
		
        HttpSession session = request.getSession(true); 
 
        this.equipe_alt_id = (Long) session.getAttribute("equipe_alt_id");
        
        if(this.equipe_alt_id != null){
    		
    		Equipe equipe = equipeService.buscarPorID(this.equipe_alt_id);
    		
    		equipesFiltradas = new ArrayList<>();
    		equipesFiltradas.add(equipe);
        }
	}
 
	public void limpar() {
		filtro = new EquipeFilter();
	}
	
	public void excluir() throws DAOException, ServiceException{
		
		try{
			equipeService.excluir(equipeSelecionada);
			
			equipesFiltradas.remove(equipeSelecionada);
			
			FacesUtil.addInfoMessage("Equipe " + equipeSelecionada.getNome() + " excluída com sucesso.");
		
		}catch (PersistenceException causa) {
			
			FacesUtil.addErrorMessage("Não foi possível excluir, pois a equipe está associada a algum chamado ou usuário.");
		}
	}

	public void pesquisar() {
		
			equipesFiltradas = equipeService.filtrados(filtro);
			limpar();
	}
	
	public void carregarAnalistasEquipe() {
		
		analistasEquipe = usuarioService.buscarAnalistasEquipe(this.equipeSelecionada);
}

	public List<Equipe> getEquipesFiltradas() {
		return equipesFiltradas;
	}

	public EquipeFilter getFiltro() {
		return filtro;
	}

	public Equipe getEquipeSelecionada() {
		return equipeSelecionada;
	}

	public void setEquipeSelecionada(Equipe equipeSelecionada) {
		this.equipeSelecionada = equipeSelecionada;
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("equipeSelecionada", equipeSelecionada);
	}

	public List<Usuario> getAnalistasEquipe() {
		return analistasEquipe;
	}

	public void setAnalistasEquipe(List<Usuario> analistasEquipe) {
		this.analistasEquipe = analistasEquipe;
	}
}
