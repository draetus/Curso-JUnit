package suites;

import org.junit.runners.Suite.SuiteClasses;

import services.CalculadoraTest;
import services.CalculoValorLocacaoTest;
import services.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {
	
}
