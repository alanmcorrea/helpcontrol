package es.indra.helpcontrol.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
import es.indra.helpcontrol.dao.filter.ChamadoFilter;
import es.indra.helpcontrol.model.Chamado;
import es.indra.helpcontrol.model.Equipe;

@Transactional
@Repository("chamadoDAO")
public class ChamadoDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED) //Injeção de dependência
	private EntityManager entityManager;
	
	public void salvar(Chamado chamado) throws DAOException  {

		try{
			entityManager.merge(chamado);
		}catch(Exception causa){
			throw new DAOException("Não foi possível salvar.", causa);
		}
	}

	public void excluir(Chamado chamado) throws DAOException {
		
		try{
			Chamado chamadoExcluir = buscarPorID(chamado.getId());
	
				entityManager.remove(chamadoExcluir);
				entityManager.flush();
				
		}catch(Exception causa){
			throw new DAOException("Não foi possível excluir.", causa);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Chamado> filtrados(ChamadoFilter filtro) {
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Chamado.class)
				// fazemos uma associação (join) com usuario e nomeamos como "u"
				.createAlias("usuario", "u")
				// fazemos uma associação (join) com regional e nomeamos como "r"
				.createAlias("regional", "r")
				// fazemos uma associação (join) com produto e nomeamos como "p"
				.createAlias("produto", "p")
				// fazemos uma associação (join) com procedimento e nomeamos como "pcd"
				.createAlias("procedimento", "pcd")
				// fazemos uma associação (join) com categoria e nomeamos como "c"
				.createAlias("categoria", "c")
				// fazemos uma associação (join) com equipe e nomeamos como "e"
				.createAlias("equipe", "e");
		
		if (StringUtils.isNotBlank(filtro.getIncidente())) {
			criteria.add(Restrictions.ilike("incidente", filtro.getIncidente(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("data", filtro.getDataDe()));
		}
		
		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("data", filtro.getDataAte()));
		}
		
		if (filtro.getEquipe() != null) {
			// acessamos o id da equipe associada ao chamado pelo alias "e", criado anteriormente
			criteria.add(Restrictions.eq("e.id", filtro.getEquipe().getId()));
		}	
		
		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			// acessamos o nome do analista associado ao chamado pelo alias "a", criado anteriormente
			criteria.add(Restrictions.ilike("u.nome", filtro.getNomeUsuario(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getRegional() != null) {
			// acessamos o id da regional associada ao chamado pelo alias "r", criado anteriormente
			criteria.add(Restrictions.eq("r.id", filtro.getRegional().getId()));
		}
		
//		if (StringUtils.isNotBlank(filtro.getNomeCategoria())) {
//			// acessamos o nome da categoria associado ao chamado pelo alias "c", criado anteriormente
//			criteria.add(Restrictions.ilike("c.categoria", filtro.getNomeCategoria(), MatchMode.ANYWHERE));
//		}
		
		if (filtro.getCategoria() != null) {
			// acessamos o id da categoria associada ao chamado pelo alias "c", criado anteriormente
			criteria.add(Restrictions.eq("c.id", filtro.getCategoria().getId()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeProduto())) {
			// acessamos o nome do produto associado ao chamado pelo alias "p", criado anteriormente
			criteria.add(Restrictions.ilike("p.nome", filtro.getNomeProduto(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getAtualizacaoProcedimentos() != null && filtro.getAtualizacaoProcedimentos().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum AtualizacaoProcedimento
			criteria.add(Restrictions.in("atualizacaoProcedimento", filtro.getAtualizacaoProcedimentos()));
		}
		
		if (filtro.getProcedimento() != null) {
			// acessamos o id do procedimento associado ao chamado pelo alias "pcd", criado anteriormente
			criteria.add(Restrictions.eq("pcd.id", filtro.getProcedimento().getId()));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusChamado
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.desc("id")).setMaxResults(500).list();
	}
	
	public List<Chamado> filtrarPorEquipesAnalista(ChamadoFilter filtro){
		
		List<Chamado> chamadosFiltrados = filtrados(filtro);
		
		List<Chamado> chamadosFiltradosPorEquipe = new ArrayList<>();
		
		List<Chamado> chamadosFiltradosPorEquipeAnalista = new ArrayList<>();

		for(Equipe equipe: filtro.getEquipes()){
			
			List<Chamado> listaAuxiliar =   entityManager.createQuery("from Chamado where equipe.id = :equipe_id", Chamado.class)
					.setParameter("equipe_id", equipe.getId()).getResultList(); 
			
			if(listaAuxiliar != null){
				chamadosFiltradosPorEquipe.addAll(listaAuxiliar);
			}
		}
			
		for(int i=0; i < chamadosFiltrados.size(); i++){

			for(int j=0; j < chamadosFiltradosPorEquipe.size(); j++){
				
				if(chamadosFiltrados.get(i).getId()
				.equals(chamadosFiltradosPorEquipe.get(j).getId())){
					
					chamadosFiltradosPorEquipeAnalista.add(chamadosFiltrados.get(i));
					
				}
			}
		}
		
		
		return chamadosFiltradosPorEquipeAnalista;
	}

	@SuppressWarnings("unchecked")
	public List<Chamado> buscarTodos() {
		
			 Query consulta = entityManager.createQuery("from Chamado");
			 
			return consulta.setMaxResults(500).getResultList();		
	}

	public Chamado buscarPorID(Long id) throws DAOException {
		
		try{
			
		return entityManager.find(Chamado.class, id);
		
		}catch(Exception causa){
			causa.printStackTrace();
			throw new DAOException("Não foi possível buscar por id.", causa);
		}
	}
	
	public Chamado buscarPorIncidenteEspecifico(String incidente) {
		
		try{
			
			return  entityManager.createQuery("from Chamado where incidente = :incidente", Chamado.class)
				.setParameter("incidente", incidente).getSingleResult();
		}
		catch(NoResultException e){
			
			return null;
		}
	}
}
