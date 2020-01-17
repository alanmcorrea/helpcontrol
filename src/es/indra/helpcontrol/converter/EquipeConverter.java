package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Equipe;
import es.indra.helpcontrol.service.EquipeService;

@Component
public class EquipeConverter implements Converter {

	@Autowired
	private EquipeService equipeService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Equipe retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = equipeService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Equipe equipe = (Equipe) value;
			
			return equipe.getId() == null ? null : equipe.getId().toString();
		}
		
		return "";
	}

}