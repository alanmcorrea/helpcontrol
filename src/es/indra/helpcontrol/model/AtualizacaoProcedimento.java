package es.indra.helpcontrol.model;

public enum AtualizacaoProcedimento {

		NAO("NÃO"),
        SIM("SIM");
	
	private String descricao;

	AtualizacaoProcedimento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}