package es.indra.helpcontrol.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import es.indra.helpcontrol.model.Usuario;

public class UsuarioSistema extends User{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getChave(), usuario.getSenha(), authorities);
		
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
