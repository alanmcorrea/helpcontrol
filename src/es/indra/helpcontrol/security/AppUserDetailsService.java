package es.indra.helpcontrol.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.indra.helpcontrol.dao.UsuarioDAO;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.model.Usuario;

public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Override
	public UserDetails loadUserByUsername(String chave) throws UsernameNotFoundException {

		Usuario usuario = usuarioDAO.buscarPorChave(chave);
		UsuarioSistema user = null;
		
		if(usuario != null){
			
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
	
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for(Grupo grupo : usuario.getGrupos()){
			
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
			System.out.println("AUTHORITIES: " + authorities.get(0).getAuthority());
		}
		
		return authorities;
	}
}
