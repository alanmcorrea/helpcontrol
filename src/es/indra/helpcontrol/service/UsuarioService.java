package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.UsuarioDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.UsuarioFilter;
import es.indra.helpcontrol.encryption.Criptografia;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.model.TrocaSenha;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.security.Seguranca;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;


@Service
public class UsuarioService {

	//Injeção de dependência
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private Seguranca seguranca;
	
	@Autowired
	private Criptografia criptografia;
	
	String senhaInicial = "Indra1234";
	
	String senhaInicialCriptogradafa = null;
	
	public void salvar(Usuario usuario) throws ServiceException, NoResultException{
		
		//Verificações
		
		Usuario usuarioExistente = usuarioDAO.buscarPorNomeEspecifico(usuario.getNome());
		
		this.senhaInicialCriptogradafa = criptografia.getMD5(senhaInicial);
		
		usuario.setChave(usuario.getChave().toUpperCase());
		
		try {

			//Regra de negócio
			
			if(usuario.getId() == null){
				
				if(usuarioExistente != null){
					
					throw new ServiceException("usuario já existe!");
				}else{
					
					usuario.setSenha(this.senhaInicialCriptogradafa);
					
					usuarioDAO.salvar(usuario);
				}
				
			}else{
		
				usuarioDAO.salvar(usuario);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Usuario> buscarTodos() {
		
		return usuarioDAO.buscarTodos();

	}
	
	public List<Usuario> buscarUsuariosAtivos() {
		
		return usuarioDAO.buscarUsuariosAtivos();
	}
	
	public List<Usuario> buscarAnalistasAtivos() {
		
		List<Grupo> gruposUsuario = seguranca.getGruposUsuario();
		
		for(Grupo grupo: gruposUsuario){
			
			if(grupo.getId().equals(3L)){
				
				return usuarioDAO.buscarAnalistasAtivos();
			}
		}
		
		return usuarioDAO.buscarAnalistasAtivosDaMesmaEquipeUsuarioLogado();
	}
	
	public void excluir(Usuario usuario) throws ServiceException, DAOException {
		
		try {
			usuarioDAO.excluir(usuario);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o analista está associado a algum chamado.", causa);
		}		
	}

	public Usuario buscarPorID(Long id) throws DAOException {

		return usuarioDAO.buscarPorID(id);
	}

	public List<Usuario> analistasFiltrados(UsuarioFilter filtro) throws DAOException {
		
		return usuarioDAO.analistasFiltrados(filtro);
	}
	
	public List<Usuario> supervisoresFiltrados(UsuarioFilter filtro) {
		
		return usuarioDAO.supervisoresFiltrados(filtro);
	}
	
	public List<Usuario> administradoresFiltrados(UsuarioFilter filtro) {
		
		return usuarioDAO.administradoresFiltrados(filtro);
	}

	public Usuario buscarPorChave(String chave) {
		
		return usuarioDAO.buscarPorChave(chave);
	}

	public List<Usuario> buscarAnalistasEquipe(Equipe equipe) {
		
		return usuarioDAO.buscarAnalistasEquipe(equipe);
	}

	public void trocarSenha(TrocaSenha trocaSenha, Usuario usuario) throws DAOException {
		
		this.senhaInicialCriptogradafa = criptografia.getMD5(trocaSenha.getNovaSenha());
		
		trocaSenha.setNovaSenha(this.senhaInicialCriptogradafa);
		
		usuarioDAO.trocarSenha(trocaSenha, usuario);
	}
}
