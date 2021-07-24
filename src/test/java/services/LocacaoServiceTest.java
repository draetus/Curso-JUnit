package services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static utils.DataUtils.isMesmaData;
import static utils.DataUtils.obterDataComDiferencaDias;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import model.Filme;
import model.Usuario;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none(); 
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		var usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		//a��o
		var locacao = service.alugarFilme(usuario, filmes);
		//verifica��o
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		//cenario
		var usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
		
		//a��o
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
		
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		var usuario = new Usuario("Usuario 1");
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		
		//acao
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 1, 10.0),
				new Filme("Filme 2", 1, 10.0),
				new Filme("Filme 3", 1, 10.0));
		var usuario = new Usuario("Usuario 1");
		
		//acao
		var locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(27.5, locacao.getValor().doubleValue(), 0.01);
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 1, 10.0),
				new Filme("Filme 2", 1, 10.0),
				new Filme("Filme 3", 1, 10.0),
				new Filme("Filme 4", 1, 10.0));
		var usuario = new Usuario("Usuario 1");
		
		//acao
		var locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(32.5, locacao.getValor().doubleValue(), 0.01);
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 1, 10.0),
				new Filme("Filme 2", 1, 10.0),
				new Filme("Filme 3", 1, 10.0),
				new Filme("Filme 4", 1, 10.0),
				new Filme("Filme 5", 1, 10.0));
		var usuario = new Usuario("Usuario 1");
		
		//acao
		var locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(35.0, locacao.getValor().doubleValue(), 0.01);
	}
	
	@Test
	public void devePagarPctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 1, 10.0),
				new Filme("Filme 2", 1, 10.0),
				new Filme("Filme 3", 1, 10.0),
				new Filme("Filme 4", 1, 10.0),
				new Filme("Filme 5", 1, 10.0),
				new Filme("Filme 6", 1, 10.0));
		var usuario = new Usuario("Usuario 1");
		
		//acao
		var locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(35.0, locacao.getValor().doubleValue(), 0.01);
	}
}
