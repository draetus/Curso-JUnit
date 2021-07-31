package services;

import static builders.FilmeBuilder.umFilme;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.LocacaoDAO;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import model.Filme;
import model.Usuario;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spcService;
	
	@Parameter(value = 0)
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	private static Filme filme7 = umFilme().agora();
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object [][] {
			{ Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
			{ Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{ Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto" }
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		var usuario = new Usuario("Usuario 1");
		
		//acao
		var locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valorLocacao, locacao.getValor(), 0.01);
	}

}
