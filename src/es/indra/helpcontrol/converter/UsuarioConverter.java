package es.indra.helpcontrol.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.service.UsuarioService;

@Component
public class UsuarioConverter implements Converter {

	@Autowired
	private UsuarioService UsuarioService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;
		
		if (value != null) {
			
			Long id = new Long(value);
			
			try {
				
				retorno = UsuarioService.buscarPorID(id);
				
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			Usuario Usuario = (Usuario) value;
			
			return Usuario.getId() == null ? null : Usuario.getId().toString();
		}
		
		return "";
	}

}