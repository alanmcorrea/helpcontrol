package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.GrupoDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class GrupoService {

	//Injeção de dependência
	@Autowired
	private GrupoDAO grupoDAO;
	
	public void salvar(Grupo grupo) throws ServiceException, NoResultException{
		
		//Verificações
		
		Grupo grupoExistente = grupoDAO.buscarPorNomeEspecifico(grupo.getNome());
		
		try {

			//Regra de negócio
			
			if(grupo.getId() == null){
				
				if(grupoExistente != null){
					
					FacesUtil.addErrorMessage("Já existe um grupo com este nome!");
					throw new ServiceException("Grupo já existe!");
				}else{
					
					grupoDAO.salvar(grupo);
				}
				
			}else{

				grupoDAO.salvar(grupo);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Grupo> buscarTodos() {
		
		return grupoDAO.buscarTodos();

	}

	public void excluir(Grupo grupo) throws ServiceException, DAOException {

		try {
			grupoDAO.excluir(grupo);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o grupo está associada a algum usuário.", causa);
		}
		
	}

	public Grupo buscarPorID(Long id) throws DAOException {

		return grupoDAO.buscarPorID(id);
	}

	public Grupo buscarPorNomeGrupo(String nome) {
		
		return grupoDAO.buscarPorNomeEspecifico(nome);
	}
}
