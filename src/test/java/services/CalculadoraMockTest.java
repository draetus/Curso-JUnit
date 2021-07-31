package services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class CalculadoraMockTest {
	
	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		Mockito.when(calc.somar(Matchers.eq(1), Matchers.anyInt())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 10000));
	}

}
