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
import es.indra.helpcontrol.dao.filter.RegionalFilter;
import es.indra.helpcontrol.model.Regional;

@Transactional
@Repository("regionalDAO")
public class RegionalDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;

	public void salvar(Regional regional) throws DAOException  {

		try{
			entityManager.merge(regional);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Regional regional) throws DAOException {
		
		try{
			Regional regionalExcluir = buscarPorID(regional.getId());
	
				entityManager.remove(regionalExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a regional está associada a algum chamado.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Regional> filtrados(RegionalFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Regional.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Regional> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Regional");
			 
			return consulta.getResultList();		
	}

	public Regional buscarPorID(Long id) throws DAOException {
		
		try{
	
			return entityManager.find(Regional.class, id);

		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Regional buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Regional where nome = :nome", Regional.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
}
