package es.indra.helpcontrol.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.model.Status;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.service.GrupoService;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
@Scope("view")
public class CadastroAdministradorController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoService grupoService;

	private Usuario usuario;
	private Grupo grupoAdministrador;

	public CadastroAdministradorController() {
		limpar();
	}

	public void inicializar() throws DAOException {
		
		//Seta o grupo ADMINISTRADORES
		
		grupoAdministrador = grupoService.buscarPorID(3L); //ID 3 = ADMINISTRADORES
	}

	private void limpar() {
		
		this.usuario = new Usuario();
	}

	public String salvar() throws ServiceException, DAOException {
		
		boolean editando = false;
		
		if(isEditando()){
			
			editando = true;
		
		}
		
		this.usuario.getGrupos().add(grupoAdministrador);
		this.usuario.setFuncaoSupervisor("NAO");
		this.usuario.setFuncaoAdministrador("SIM");
		this.usuarioService.salvar(this.usuario);

		FacesUtil.addInfoMessage("Administrador salvo com sucesso!");
		
		if (editando){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			HttpSession session = request.getSession(true);  
			
			session.setAttribute("usuario_alt_id", this.usuario.getId());
			
			//return "/usuarios/Pesquisausuario.xhtml?usuario_alt_id=" + this.usuario.getId() + "&faces-redirect=true";
			
			limpar();
			
			return "/administradores/PesquisaAdministrador";
			
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
}
