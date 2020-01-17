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
import es.indra.helpcontrol.dao.filter.ProdutoFilter;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Produto;

@Transactional
@Repository("produtoDAO")
public class ProdutoDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	public void salvar(Produto produto) throws DAOException  {

		try{
			entityManager.merge(produto);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Produto produto) throws DAOException {
		
		try{
			Produto produtoExcluir = buscarPorID(produto.getId());
	
				entityManager.remove(produtoExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o produto está associado a algum chamado.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(ProdutoFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Produto");
			 
			return consulta.getResultList();		
	}

	public Produto buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Produto.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Produto buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Produto where nome = :nome", Produto.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
	
	public List<Produto> buscarProdutoDe(Categoria categoria) {
		
		return entityManager.createQuery("from Produto p where p.categoria = :categoria", 
				Produto.class).setParameter("categoria", categoria).getResultList();
	}
}
