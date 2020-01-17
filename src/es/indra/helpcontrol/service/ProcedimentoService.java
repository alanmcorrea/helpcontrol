package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.ProcedimentoDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.ProcedimentoFilter;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class ProcedimentoService {

	//Injeção de dependência
	@Autowired
	private ProcedimentoDAO procedimentoDAO;
	
	public void salvar(Procedimento procedimento) throws ServiceException, NoResultException{
		
		//Verificações
		
		Procedimento procedimentoExistente = procedimentoDAO.buscarPorNomeEspecifico(procedimento.getNome());
		
		try {

			//Regra de negócio
			
			if(procedimento.getId() == null){
				
				if(procedimentoExistente != null){
					
					FacesUtil.addErrorMessage("Já existe um procedimento com este nome!");
					throw new ServiceException("Procedimento já existe!");
					
				}else{
					
					procedimentoDAO.salvar(procedimento);
				}
				
			}else{

				procedimentoDAO.salvar(procedimento);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Procedimento> buscarTodos() {
		
		return procedimentoDAO.buscarTodos();

	}

	public void excluir(Procedimento procedimento) throws ServiceException, DAOException {

		try {
			procedimentoDAO.excluir(procedimento);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o procedimento está associado a algum chamado.", causa);
		}
		
	}

	public Procedimento buscarPorID(Long id) throws DAOException {

		return procedimentoDAO.buscarPorID(id);
	}

	public List<Procedimento> filtrados(ProcedimentoFilter filtro) throws DAOException {
		
		return procedimentoDAO.filtrados(filtro);
	}

	public Procedimento buscarPorNomeProcedimento(String nome) {
		
		return procedimentoDAO.buscarPorNomeEspecifico(nome);
	}
}
