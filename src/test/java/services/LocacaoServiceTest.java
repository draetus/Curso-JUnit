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

import model.Filme;
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
		
		//a��o
		var locacao = service.alugarFilme(usuario, filme);
		//verifica��o
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = Exception.class)
	public void testeLocacaoFilmeSemEstoque() throws Exception {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 0, 5.0);
		
		//a��o
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testeLocacaoFilmeSemEstoque2() {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 0, 5.0);
		
		//a��o
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lan�ado uma exce��o");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	@Test
	public void testeLocacaoFilmeSemEstoque3() throws Exception {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 0, 5.0);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//a��o
		service.alugarFilme(usuario, filme);
	}
}
