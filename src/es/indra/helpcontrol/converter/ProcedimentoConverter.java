package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Procedimento;
import es.indra.helpcontrol.service.ProcedimentoService;

@Component
public class ProcedimentoConverter implements Converter {

	@Autowired
	private ProcedimentoService procedimentoService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Procedimento retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = procedimentoService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Procedimento procedimento = (Procedimento) value;
			
			return procedimento.getId() == null ? null : procedimento.getId().toString();
		}
		
		return "";
	}

}