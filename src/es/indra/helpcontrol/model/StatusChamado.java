package es.indra.helpcontrol.model;

public enum StatusChamado {

		Repassado("Repassado"),
        Resolvido("Resolvido"),
        Cancelado("Cancelado");
	
	private String descricao;

	StatusChamado(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}