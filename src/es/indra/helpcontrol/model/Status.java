package es.indra.helpcontrol.model;

public enum Status {

	Ativo("Ativo"), 
	Inativo("Inativo");
	
	private String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}