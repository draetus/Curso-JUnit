package dao;

import java.util.List;

import model.Locacao;

public interface LocacaoDAO {
	
	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();

}
