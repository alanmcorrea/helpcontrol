package es.indra.helpcontrol.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.UsuarioFilter;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.model.TrocaSenha;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.security.Seguranca;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Transactional
@Repository("UsuarioDAO")
public class UsuarioDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	@Autowired
	Seguranca seguranca;
	
	public void salvar(Usuario Usuario) throws DAOException  {

		try{
			entityManager.merge(Usuario);
		}catch(Exception causa){
			
			FacesUtil.addErrorMessage("Não foi possível salvar,"
					+ " verifique se há uma equipe selecionada,"
					+ " se todos os campos foram preenchidos ou "
					+ "outros requisitos de negócio.");
			
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Usuario Usuario) throws DAOException{
		
		try{
			Usuario UsuarioExcluir = buscarPorID(Usuario.getId());
	
				entityManager.remove(UsuarioExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o analista está associado a algum chamado.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> analistasFiltrados(UsuarioFilter filtro) throws DAOException {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		filtro.setFuncaoSupervisor("NAO");
		filtro.setFuncaoAdministrador("NAO");
		
		if (StringUtils.isNotBlank(filtro.getFuncaoSupervisor())) {			
			criteria.add(Restrictions.eq("funcaoSupervisor", filtro.getFuncaoSupervisor()));
		}
		
		if (StringUtils.isNotBlank(filtro.getFuncaoAdministrador())) {			
			criteria.add(Restrictions.eq("funcaoAdministrador", filtro.getFuncaoAdministrador()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusUsuarios
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> supervisoresFiltrados(UsuarioFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		filtro.setFuncaoSupervisor("SIM");
		filtro.setFuncaoAdministrador("NAO");
		
		if (StringUtils.isNotBlank(filtro.getFuncaoSupervisor())) {			
			criteria.add(Restrictions.eq("funcaoSupervisor", filtro.getFuncaoSupervisor()));
		}
		
		if (StringUtils.isNotBlank(filtro.getFuncaoAdministrador())) {			
			criteria.add(Restrictions.eq("funcaoAdministrador", filtro.getFuncaoAdministrador()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusUsuarios
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> administradoresFiltrados(UsuarioFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		filtro.setFuncaoSupervisor("NAO");
		filtro.setFuncaoAdministrador("SIM");
		
		if (StringUtils.isNotBlank(filtro.getFuncaoSupervisor())) {			
			criteria.add(Restrictions.eq("funcaoSupervisor", filtro.getFuncaoSupervisor()));
		}
		
		if (StringUtils.isNotBlank(filtro.getFuncaoAdministrador())) {			
			criteria.add(Restrictions.eq("funcaoAdministrador", filtro.getFuncaoAdministrador()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusUsuarios
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Usuario");
			 
			return consulta.getResultList();		
	}
	
	public List<Usuario> buscarUsuariosAtivos() {
		
		try{
			
			return  entityManager.createQuery("from Usuario where status = 'Ativo'", Usuario.class).getResultList();
		}
		catch(Exception e){
			
			return null;
		}
	}
	
	public List<Usuario> buscarAnalistasAtivos() {
		
		try{
			
			return  entityManager.createQuery("from Usuario where status = 'Ativo' AND funcaoSupervisor = 'NAO' AND funcaoAdministrador = 'NAO'", Usuario.class).getResultList();
		}
		catch(NoResultException e){
			
			return null;
		}
	}
	
	public List<Usuario> buscarAnalistasAtivosDaMesmaEquipeUsuarioLogado() {
		
		List<Equipe> equipesUsuarioLogado = seguranca.getEquipesUsuario();
		List<Usuario> analistasAtivosDaMesmaEquipeUsuarioLogado = new ArrayList<>();
		List<Usuario> analistasAtivosTratamento = new ArrayList<>();
		List<Usuario> analistasAtivos = new ArrayList<>();
		
		for(Equipe equipe: equipesUsuarioLogado){
			
			List<Usuario> analistasAux = entityManager
					.createQuery("from Usuario where status = 'Ativo' AND funcaoSupervisor = 'NAO' AND funcaoAdministrador = 'NAO'", Usuario.class)
					//.setParameter("equipe_id", equipe.getId())
					.getResultList();
			
			analistasAtivos.addAll(analistasAux);
		}
		
		boolean jaEstaNaLista = false;
		
		for(int i=0; i < analistasAtivos.size(); i++){

			for(int j=0; j < equipesUsuarioLogado.size(); j++){
				
				List<Equipe> equipesAnalistasAtivos = analistasAtivos.get(i).getEquipes();
				
				for(int l=0; l < equipesAnalistasAtivos.size(); l++){
				
					if(equipesAnalistasAtivos.get(l).getId()
					.equals(equipesUsuarioLogado.get(j).getId())){
						
						for(Usuario analista: analistasAtivosDaMesmaEquipeUsuarioLogado){
						
							if(analista.getId().equals(analistasAtivos.get(i).getId())){
							
								jaEstaNaLista = true;
							}
						}
						
						if(!jaEstaNaLista){
							
							analistasAtivosDaMesmaEquipeUsuarioLogado.add(analistasAtivos.get(i));
							
						}
						
						jaEstaNaLista = false;
					}
				}
			}
		}
		
		return analistasAtivosDaMesmaEquipeUsuarioLogado;
	}

	public Usuario buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Usuario.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Usuario buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Usuario where nome = :nome", Usuario.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}

	public Usuario buscarPorChave(String chave) {
		
		try{
			
			return  entityManager.createQuery("from Usuario where lower(chave) = :chave", Usuario.class)
				.setParameter("chave", chave.toLowerCase()).getSingleResult();
		}
		catch(NoResultException e){
			
			//Nenhum usuário encontrado com a chave informada.
			
			return null;
		}
	}

	public List<Usuario> buscarAnalistasEquipe(Equipe equipe) {
		
		List<Usuario> usuarios = new ArrayList<>();
		List<Usuario> usuariosEquipe = new ArrayList<>();
		
		usuarios = entityManager.createQuery("from Usuario", Usuario.class).getResultList();
		
		for(Usuario usuario: usuarios){
			
			for(int i = 0; i < usuario.getEquipes().size(); i++){
				
				if(usuario.getEquipes().get(i).getId().equals(equipe.getId())){
					
					usuariosEquipe.add(usuario);
				}
			}
		}
		
		return usuariosEquipe;
	}

	public void trocarSenha(TrocaSenha trocaSenha, Usuario usuario) throws DAOException {
		
		Usuario usuarioTrocarSenha = buscarPorID(usuario.getId());
		
		usuarioTrocarSenha.setSenha(trocaSenha.getNovaSenha());
		
		salvar(usuarioTrocarSenha);

	}
}
