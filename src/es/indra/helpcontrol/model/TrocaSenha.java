package es.indra.helpcontrol.model;

public class TrocaSenha {

	private String senhaAtual;
	private String novaSenha;
	private String confirmacaoNovaSenha;
	public String getSenhaAtual() {
		return senhaAtual;
	}
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}
	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}	
}
