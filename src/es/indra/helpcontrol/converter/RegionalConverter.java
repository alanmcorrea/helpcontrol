package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Regional;
import es.indra.helpcontrol.service.RegionalService;

@Component
public class RegionalConverter implements Converter {

	@Autowired
	private RegionalService regionalService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Regional retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = regionalService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Regional regional = (Regional) value;
			
			return regional.getId() == null ? null : regional.getId().toString();
		}
		
		return "";
	}

}