package es.indra.helpcontrol.dao;

import java.io.Serializable;
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
import es.indra.helpcontrol.dao.filter.CategoriaFilter;
import es.indra.helpcontrol.model.Categoria;

@Transactional
@Repository("categoriaDAO")
public class CategoriaDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	public void salvar(Categoria categoria) throws DAOException  {

		try{
			entityManager.merge(categoria);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Categoria categoria) throws DAOException {
		
		try{
			Categoria categoriaExcluir = buscarPorID(categoria.getId());
	
				entityManager.remove(categoriaExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a categoria está associada a algum produto.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> filtrados(CategoriaFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Categoria.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Categoria");
			 
			return consulta.getResultList();		
	}

	public Categoria buscarPorID(Long id) throws DAOException {
		
		try{

		return entityManager.find(Categoria.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Categoria buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Categoria where nome = :nome", Categoria.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
}
