package es.indra.helpcontrol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Grupo;

@Transactional
@Repository("grupoDAO")
public class GrupoDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	public void salvar(Grupo grupo) throws DAOException  {

		try{
			entityManager.merge(grupo);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Grupo grupo) throws DAOException {
		
		try{
			Grupo grupoExcluir = buscarPorID(grupo.getId());
	
				entityManager.remove(grupoExcluir);
				entityManager.flush();
				
		}catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o grupo está associado a algum usuário.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Grupo");
			 
			return consulta.getResultList();		
	}

	public Grupo buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Grupo.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Grupo buscarPorNomeEspecifico(String nome) {
		
		try{
			
			return  entityManager.createQuery("from Grupo where nome = :nome", Grupo.class)
				.setParameter("nome", nome).getSingleResult();
		}
		catch(Exception e){
			
			return null;
		}
	}
}
