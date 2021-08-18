package services;

import static builders.FilmeBuilder.umFilme;
import static builders.UsuarioBuilder.umUsuario;
import static matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dao.LocacaoDAO;
import model.Filme;
import model.Locacao;
import model.Usuario;
import utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class})
public class LocacaoServiceTest_PowerMock {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private SPCService spc;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private EmailService email;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none(); 
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = PowerMockito.spy(service);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
//		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DataUtils.obterData(28, 4, 2017));
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		error.checkThat(locacao.getDataLocacao(), ehHoje());
//		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
		
	}
	
	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DataUtils.obterData(29, 4, 2017));
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(locacao.getDataRetorno(), caiNumaSegunda());
//		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
		
		PowerMockito.verifyStatic(Calendar.class, Mockito.times(2));
		calendar.getInstance();
		
	}
	
	@Test
	public void deveAlugarFilme_SemCalcularValor() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);
		
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes); 
		
	}
	
//	@Test
//	private void deveCalcularValorLocacao() throws Exception {
//		//cenario
//		List<Filme> filmes = Arrays.asList(umFilme().agora());
//		
//		//acao
//		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
//		
//		//verificacao
//		assertThat(valor, is(4.0));
//	}
}
