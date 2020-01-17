package es.indra.helpcontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.model.Status;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.EquipeService;
import es.indra.helpcontrol.service.GrupoService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroSupervisorController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private GrupoService grupoService;

	private Usuario usuario;
	private Grupo grupoSupervisor;
	
	private DualListModel<String> equipesPickList; 
	
	List<String> sourceEquipes;
	List<String> targetEquipes;

	public CadastroSupervisorController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
		//Seta o grupo SUPERVISORES
		
		grupoSupervisor = grupoService.buscarPorID(2L); //ID 2 = SUPERVISORES
		
		//Carrega todas as equipes
		
		List<Equipe> todasEquipes = equipeService.buscarTodos();
		
		//Prepara o PickList para carregar como cadastro novo
		
		this.sourceEquipes = new ArrayList<>(); 
		this.targetEquipes = new ArrayList<>(); 
		
		if(usuario == null){
		
			for(Equipe e: todasEquipes){
			
				this.sourceEquipes.add(e.getNome());
			}
		}
		
		//Prepara o PickList para carregar como edição de cadastro
		
		if(usuario != null){
		
			todasEquipes.removeAll(usuario.getEquipes());
						
			for(Equipe e: todasEquipes){
				
				this.sourceEquipes.add(e.getNome());
			}
			
			for(Equipe e: usuario.getEquipes()){
				
				this.targetEquipes.add(e.getNome());
			}		
		}
		
		//Instancia o PickList
		
		equipesPickList =  new DualListModel<String>(sourceEquipes, targetEquipes);		
		
	}

	private void limpar() {
		
		this.usuario = new Usuario();
		this.sourceEquipes = new ArrayList<>();
	}

	public String salvar() throws ServiceException, DAOException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		
		}
		
		List<Equipe> equipesTarget = new ArrayList<>();
		
		if(this.equipesPickList.getTarget() != null || this.equipesPickList.getTarget().size() > 0){
			
			for(String nomeEquipe: this.equipesPickList.getTarget()){
	
				Equipe temp = equipeService.buscarPorNomeEquipe(nomeEquipe);
				
				equipesTarget.add(temp);
			}
		}
		
		this.usuario.getGrupos().add(grupoSupervisor);
		this.usuario.setFuncaoSupervisor("SIM");
		this.usuario.setFuncaoAdministrador("NAO");
		
		if(equipesTarget.size() > 0){
			this.usuario.setEquipes(equipesTarget);
		}
		
		this.usuarioService.salvar(this.usuario);

		FacesUtil.addInfoMessage("Supervisor salvo com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("usuario_alt_id", this.usuario.getId());
			
			//return "/usuarios/Pesquisausuario.xhtml?usuario_alt_id=" + this.usuario.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/supervisores/PesquisaSupervisor";
			
		}
		
		limpar();
		
		return null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isEditando(){
		return this.usuario.getId() != null;
	}
	
	public Status[] getStatuses(){
        
        return Status.values();
    }

	public DualListModel<String> getEquipesPickList() {
		return equipesPickList;
	}

	public void setEquipesPickList(DualListModel<String> equipesPickList) {
		this.equipesPickList = equipesPickList;
	}
}
