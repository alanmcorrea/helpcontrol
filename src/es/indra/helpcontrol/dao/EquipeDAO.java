package es.indra.helpcontrol.dao;

import java.util.List;

import javax.persistence.EntityManager;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.EquipeFilter;
import es.indra.helpcontrol.model.Equipe;

@Transactional
@Repository("equipeDAO")
public class EquipeDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;

	public void salvar(Equipe equipe) throws DAOException  {

		try{
			entityManager.merge(equipe);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Equipe equipe) throws DAOException {
		
		try{
			Equipe equipeExcluir = buscarPorID(equipe.getId());
	
				entityManager.remove(equipeExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a equipe está associada a algum analista ou chamado.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipe> filtrados(EquipeFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Equipe.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Equipe> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Equipe");
			 
			return consulta.getResultList();		
	}

	public Equipe buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Equipe.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Equipe buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Equipe where nome = :nome", Equipe.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
}
