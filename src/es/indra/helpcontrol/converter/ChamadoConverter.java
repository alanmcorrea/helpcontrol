package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Chamado;
import es.indra.helpcontrol.service.ChamadoService;

@Component
public class ChamadoConverter implements Converter {

	@Autowired
	private ChamadoService chamadoService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Chamado retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = chamadoService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Chamado chamado = (Chamado) value;
			
			return chamado.getId() == null ? null : chamado.getId().toString();
		}
		
		return "";
	}

}