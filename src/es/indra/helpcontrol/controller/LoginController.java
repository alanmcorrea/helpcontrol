package es.indra.helpcontrol.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.util.jsf.FacesUtil;


@Controller
public class LoginController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String chave;
	
	public void preRender(){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		
		if("true".equals(request.getParameter("invalid"))){
		
			FacesUtil.addErrorMessage("Usuário ou senha inválidos!");
		}
	}
	
	public void login() throws ServletException, IOException{
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login");	

		dispatcher.forward(request,  response);
		
		facesContext.responseComplete();
	}
	
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave =chave;
	}
}
