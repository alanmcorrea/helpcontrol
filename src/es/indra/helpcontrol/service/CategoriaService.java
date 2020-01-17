package es.indra.helpcontrol.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.CategoriaDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.CategoriaFilter;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class CategoriaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Injeção de dependência
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	public void salvar(Categoria categoria) throws ServiceException, NoResultException{
		
		//Verificações
		
		Categoria categoriaExistente = categoriaDAO.buscarPorNomeEspecifico(categoria.getNome());
		
		try {

			//Regra de negócio
			
			if(categoria.getId() == null){
				
				if(categoriaExistente != null){
					
					FacesUtil.addErrorMessage("Já existe uma categoria com este nome!");
					throw new ServiceException("Categoria já existe!");
				}else{
					
					categoriaDAO.salvar(categoria);
				}
				
			}else{

				categoriaDAO.salvar(categoria);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Categoria> buscarTodos() {
		
		return categoriaDAO.buscarTodos();

	}

	public void excluir(Categoria categoria) throws ServiceException, DAOException {

		try {
			categoriaDAO.excluir(categoria);
			
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a categoria está associada a algum produto.", causa);
		}
		
	}

	public Categoria buscarPorID(Long id) throws DAOException {
		
		return categoriaDAO.buscarPorID(id);
	}

	public List<Categoria> filtrados(CategoriaFilter filtro) {
		
		return categoriaDAO.filtrados(filtro);
	}

	public Categoria buscarPorNomeCategoria(String nome) {
		
		return categoriaDAO.buscarPorNomeEspecifico(nome);
	}
}
