package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.RegionalDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.RegionalFilter;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class RegionalService {

	//Injeção de dependência
	@Autowired
	private RegionalDAO regionalDAO;
	
	public void salvar(Regional regional) throws ServiceException, NoResultException{
		
		//Verificações
		
		Regional regionalExistente = regionalDAO.buscarPorNomeEspecifico(regional.getNome());
		
		try {

			//Regra de negócio
			
			if(regional.getId() == null){
				
				if(regionalExistente != null){
					
					FacesUtil.addErrorMessage("Já existe uma regional com este nome!");
					throw new ServiceException("Regional já existe!");
				}else{
					
					regionalDAO.salvar(regional);
				}
				
			}else{

				regionalDAO.salvar(regional);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Regional> buscarTodos() {
		
		return regionalDAO.buscarTodos();

	}

	public void excluir(Regional regional) throws ServiceException, DAOException {

		try {
			regionalDAO.excluir(regional);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a regional está associada a algum chamado.", causa);
		}
		
	}

	public Regional buscarPorID(Long id) throws DAOException {

		return regionalDAO.buscarPorID(id);

	}

	public List<Regional> filtrados(RegionalFilter filtro) {
		
		return regionalDAO.filtrados(filtro);
	}
}
