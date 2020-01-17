package es.indra.helpcontrol.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.indra.helpcontrol.dao.exception.DAOException;
import es.indra.helpcontrol.encryption.Criptografia;
import es.indra.helpcontrol.model.TrocaSenha;
import es.indra.helpcontrol.model.Usuario;
import es.indra.helpcontrol.security.Seguranca;
import es.indra.helpcontrol.service.UsuarioService;
import es.indra.helpcontrol.service.exception.ServiceException;
import es.indra.helpcontrol.util.jsf.FacesUtil;

@Controller
//@Scope("view")
public class TrocaSenhaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Seguranca seguranca;
	
	@Autowired
	private Criptografia criptografia;

	private TrocaSenha trocaSenha;
	
	String senhaAtualCriptogradafa = null;

	public TrocaSenhaController() {
		limpar();
	}

	public void inicializar() throws DAOException {

	}

	private void limpar() {
		
		this.trocaSenha = new TrocaSenha();
	}

	public void trocarSenha() throws ServiceException, DAOException {
		
		if(this.trocaSenha.getNovaSenha() != "" 
				&& this.trocaSenha.getConfirmacaoNovaSenha() != ""
				&& this.trocaSenha.getSenhaAtual() != ""){
			
			this.senhaAtualCriptogradafa = criptografia.getMD5(this.trocaSenha.getSenhaAtual());
			
			Usuario usuario = seguranca.getUsuarioLogado().getUsuario();
			
			if(this.senhaAtualCriptogradafa.equals(usuario.getSenha())){
				
				if(this.trocaSenha.getNovaSenha().equals(this.trocaSenha.getConfirmacaoNovaSenha())){
				
					usuarioService.trocarSenha(this.trocaSenha, usuario);
					
					FacesUtil.addInfoMessage("Senha alterada com sucesso! Efetue o logout e acesse novamente com a nova senha.");
					limpar();
					
				}else{
				
					FacesUtil.addErrorMessage("A nova senha e confirmação da nova senha não conferem!");

				}
			
			}else{
				
				FacesUtil.addErrorMessage("A senha atual não foi digitada corretamente!");

			}
			
		}else{
			
			FacesUtil.addErrorMessage("Preencha todos os campos!");

		}
	}

	public TrocaSenha getTrocaSenha() {
		return trocaSenha;
	}
	
	public void setTrocaSenha(TrocaSenha trocaSenha) {
		this.trocaSenha = trocaSenha;
	}
}
