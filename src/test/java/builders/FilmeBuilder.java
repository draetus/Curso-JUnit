package builders;

import model.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme() {
		FilmeBuilder filmeBuilder = new FilmeBuilder();
		filmeBuilder.filme = new Filme();
		filmeBuilder.filme.setEstoque(2);
		filmeBuilder.filme.setNome("Filme 1");
		filmeBuilder.filme.setPrecoLocacao(4.0);
		return filmeBuilder;
	}
	
	public static FilmeBuilder umFilmeSemEstoque() {
		FilmeBuilder filmeBuilder = new FilmeBuilder();
		filmeBuilder.filme = new Filme();
		filmeBuilder.filme.setEstoque(0);
		filmeBuilder.filme.setNome("Filme 1");
		filmeBuilder.filme.setPrecoLocacao(4.0);
		return filmeBuilder;
	}
	
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}
	
	public Filme agora() {
		return filme;
	}

}
