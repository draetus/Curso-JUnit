package services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static utils.DataUtils.isMesmaData;
import static utils.DataUtils.obterDataComDiferencaDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import model.Filme;
import model.Locacao;
import model.Usuario;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none(); 
	
	@Test
	public void testeLocacao() throws Exception {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 2, 5.0);
		
		//ação
		var locacao = service.alugarFilme(usuario, filme);
		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacaoFilmeSemEstoque() throws Exception {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 0, 5.0);
		
		//ação
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
		//cenario
		var service = new LocacaoService();
		var filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		
		//acao
		service.alugarFilme(usuario, null);
	}
}
