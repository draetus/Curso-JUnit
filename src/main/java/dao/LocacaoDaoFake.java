package dao;

import java.util.Collections;
import java.util.List;

import model.Locacao;

public class LocacaoDaoFake implements LocacaoDAO {

	@Override
	public void salvar(Locacao locacao) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() {
		return Collections.emptyList();
	}

}
