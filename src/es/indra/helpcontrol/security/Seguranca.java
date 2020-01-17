package es.indra.helpcontrol.security;

import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Grupo;

@Controller
public class Seguranca {

	public String getNomeUsuario(){
		
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
			nome = usuarioLogado.getUsuario().getNome();
		}
		
		return nome;
	}
	
	public List<Grupo> getGruposUsuario(){
		
		List<Grupo> grupos = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
			
			grupos = usuarioLogado.getUsuario().getGrupos();
		}
		
		return grupos;
	}
	
	public List<Equipe> getEquipesUsuario(){
		
		List<Equipe> equipes = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
			
			equipes = usuarioLogado.getUsuario().getEquipes();
		}
		
		return equipes;
	}	

	public UsuarioSistema getUsuarioLogado() {
		
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
		FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
		
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}
	
	public boolean isAdministrador(){
		
		for(Grupo grupo: getGruposUsuario()){
			
			if(grupo.getId().equals(3L)){
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isSupervisor(){
		
		for(Grupo grupo: getGruposUsuario()){
			
			if(grupo.getId().equals(2L)){
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAnalista(){
		
		for(Grupo grupo: getGruposUsuario()){
			
			if(grupo.getId().equals(1L)){
				
				return true;
			}
		}
		
		return false;
	}
}
