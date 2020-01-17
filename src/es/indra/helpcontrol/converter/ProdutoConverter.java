package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Produto;
import es.indra.helpcontrol.service.ProdutoService;

@Component
public class ProdutoConverter implements Converter {

	@Autowired
	private ProdutoService produtoService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = produtoService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Produto produto = (Produto) value;
			
			return produto.getId() == null ? null : produto.getId().toString();
		}
		
		return "";
	}

}