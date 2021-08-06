package matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{
	
	private Integer diferencaDias;
	
	public DataDiferencaDiasMatcher(Integer diferencaDias) {
		this.diferencaDias = diferencaDias;
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diferencaDias));
	}

	public void describeTo(Description description) {
		Date dataEsperada = DataUtils.obterDataComDiferencaDias(diferencaDias);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		description.appendText(format.format(dataEsperada));
	}

}
