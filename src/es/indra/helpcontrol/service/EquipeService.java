package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.EquipeDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.EquipeFilter;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class EquipeService {

	//Injeção de dependência
	@Autowired
	private EquipeDAO equipeDAO;
	
	public void salvar(Equipe equipe) throws ServiceException, NoResultException{
		
		//Verificações
		
		Equipe equipeExistente = equipeDAO.buscarPorNomeEspecifico(equipe.getNome());
		
		try {

			//Regra de negócio
			
			if(equipe.getId() == null){
				
				if(equipeExistente != null){
					
					FacesUtil.addErrorMessage("Já existe uma equipe com este nome!");
					throw new ServiceException("Equipe já existe!");
				}else{
					
					equipeDAO.salvar(equipe);
				}
				
			}else{

				equipeDAO.salvar(equipe);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Equipe> buscarTodos() {
		
		return equipeDAO.buscarTodos();

	}

	public void excluir(Equipe equipe) throws ServiceException, DAOException {

		try {
			equipeDAO.excluir(equipe);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois a equipe está associada a algum analista ou chamado.", causa);
		}
		
	}

	public Equipe buscarPorID(Long id) throws DAOException {

		return equipeDAO.buscarPorID(id);
	}

	public List<Equipe> filtrados(EquipeFilter filtro) {
		
		return equipeDAO.filtrados(filtro);
	}

	public Equipe buscarPorNomeEquipe(String nome) {
		
		return equipeDAO.buscarPorNomeEspecifico(nome);
	}
}
