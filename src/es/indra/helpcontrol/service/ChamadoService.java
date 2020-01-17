package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.ChamadoDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.ChamadoFilter;
import es.indra.helpcontrol.model.Chamado;
import es.indra.helpcontrol.model.Grupo;
import es.indra.helpcontrol.security.Seguranca;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class ChamadoService {

	//Injeção de dependência
	@Autowired
	private ChamadoDAO chamadoDAO;
	
	@Autowired
	Seguranca seguranca;
	
	public void salvar(Chamado chamado) throws ServiceException, NoResultException{
		
		//Verificações
		
		Chamado chamadoExistente = chamadoDAO.buscarPorIncidenteEspecifico(chamado.getIncidente());
		
		try {

			//Regra de negócio
			
			if(chamado.getId() == null){
				
				if(chamadoExistente != null){
					
					FacesUtil.addErrorMessage("Este chamado já existe.");
					throw new ServiceException("Chamado já existe!");
				}else{
					
					chamadoDAO.salvar(chamado);
				}
				
			}else{
				
				chamadoDAO.salvar(chamado);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Chamado> buscarTodos() {
		
		return chamadoDAO.buscarTodos();

	}
	
	public void excluir(Chamado chamado) throws ServiceException {

		try {
			chamadoDAO.excluir(chamado);
		} catch (DAOException e) {

			throw new ServiceException(e.getMessage() ,e);
		}
		
	}

	public Chamado buscarPorID(Long id) throws DAOException {

		return chamadoDAO.buscarPorID(id);
	}

	public List<Chamado> filtrados(ChamadoFilter filtro) {
		
		filtro.setEquipes(seguranca.getEquipesUsuario());
		
		List<Grupo> gruposUsuario = seguranca.getGruposUsuario();
		
		for(Grupo grupo: gruposUsuario){
			
			if(grupo.getId().equals(3L)){
				
				return chamadoDAO.filtrados(filtro);
			}
		}
		
		return chamadoDAO.filtrarPorEquipesAnalista(filtro);
		
	}
}
