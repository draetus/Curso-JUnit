package services;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import model.Filme;
import model.Usuario;
import services.LocacaoService;
import utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		//cenario
		var service = new LocacaoService();
		var usuario = new Usuario("Usuario 1");
		var filme = new Filme("Filme 1", 2, 5.0);
		
		//ação
		var locacao = service.alugarFilme(usuario, filme);
		
		//verificação
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}

}
