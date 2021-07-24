package services;

import static utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import model.Filme;
import model.Locacao;
import model.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}
		
		
		var locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for (int i=0; i<filmes.size(); i++) {
			var filme = filmes.get(i);
			Double valorFilme = calculaValorDescontadoFilme(i, filme.getPrecoLocacao());
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		var dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
	private Double calculaValorDescontadoFilme(int i, Double valorFilme) {
		switch(i) {
			case 2: return valorFilme * 0.75;
			case 3: return valorFilme * 0.50;
			case 4: return valorFilme * 0.25;
			case 5: return 0.0;
			default: return valorFilme;
		}
	}
}