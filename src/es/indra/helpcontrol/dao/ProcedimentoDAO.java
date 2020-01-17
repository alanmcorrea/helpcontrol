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
import es.indra.helpcontrol.dao.filter.ProcedimentoFilter;
import es.indra.helpcontrol.model.Procedimento;

@Transactional
@Repository("procedimentoDAO")
public class ProcedimentoDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	public void salvar(Procedimento procedimento) throws DAOException  {

		try{
			entityManager.merge(procedimento);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Procedimento procedimento) throws DAOException {
		
		try{
			Procedimento procedimentoExcluir = buscarPorID(procedimento.getId());
	
				entityManager.remove(procedimentoExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o procedimento está associado a algum chamado.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Procedimento> filtrados(ProcedimentoFilter filtro) throws DAOException {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Procedimento.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));	
		}
		
		criteria.add(Restrictions.ne("nome", buscarPorID(1L).getNome()));
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Procedimento> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Procedimento order by id asc");
			 
			return consulta.getResultList();		
	}

	public Procedimento buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Procedimento.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Procedimento buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Procedimento where nome = :nome", Procedimento.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
}
