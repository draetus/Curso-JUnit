package services;

import static utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.LocacaoDAO;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import model.Filme;
import model.Locacao;
import model.Usuario;
import utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	
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
		
		boolean negativado;
		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente");
		}
		
		if (negativado) {
			throw new LocadoraException("Usuário Negativado");
		}
		
		
		
		var locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for (var i=0; i<filmes.size(); i++) {
			var filme = filmes.get(i);
			Double valorFilme = calculaValorDescontadoFilme(i, filme.getPrecoLocacao());
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		var dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao); 
		
		return locacao;
	}
	
	public void notificatAtrasos() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		for (Locacao locacao : locacoes) {
			if (locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}
	
	public void prorrogarLocacao(Locacao locacao, int dias) {
		var novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(DataUtils.adicionarDias(new Date(), dias));
		novaLocacao.setValor(locacao.getValor() * dias);
		dao.salvar(novaLocacao);
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