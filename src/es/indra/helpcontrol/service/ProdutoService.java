package es.indra.helpcontrol.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.helpcontrol.dao.ProdutoDAO;
import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.dao.filter.ProdutoFilter;
import es.indra.helpcontrol.model.Categoria;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

/**
 *
 * 
 * @author v8z4
 *
 */

@Service
public class ProdutoService {

	//Injeção de dependência
	@Autowired
	private ProdutoDAO produtoDAO;
	
	public void salvar(Produto produto) throws ServiceException, NoResultException{
		
		//Verificações
		
		Produto produtoExistente = produtoDAO.buscarPorNomeEspecifico(produto.getNome());
		
		try {

			//Regra de negócio
			
			if(produto.getId() == null){
				
				if(produtoExistente != null){
					
					FacesUtil.addErrorMessage("Já existe um produto com este nome!");
					throw new ServiceException("Produto já existe!");
				}else{
					
					produtoDAO.salvar(produto);
				}
				
			}else{

				produtoDAO.salvar(produto);
			}
						
		} catch (DAOException e) {
			// Gerar uma nova exception ServiceException
			throw new ServiceException(e);
		}
	}
	
	public List<Produto> buscarTodos() {
		
		return produtoDAO.buscarTodos();

	}

	public void excluir(Produto produto) throws ServiceException, DAOException {

		try {
			produtoDAO.excluir(produto);
		} catch(PersistenceException causa){
			throw new PersistenceException("Não foi possível excluir, pois o produto está associado a algum chamado.", causa);
		}
		
	}

	public Produto buscarPorID(Long id) throws DAOException {

		return produtoDAO.buscarPorID(id);
	}

	public List<Produto> filtrados(ProdutoFilter filtro) {
		
		return produtoDAO.filtrados(filtro);
	}

	public Produto buscarPorNomeProduto(String nome) {
		
		return produtoDAO.buscarPorNomeEspecifico(nome);
	}
	
	public List<Produto> buscarProdutoDe(Categoria categoria) {
		
		return produtoDAO.buscarProdutoDe(categoria);
	}
}
