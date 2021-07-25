package matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{
	
	private Integer diferencaDias;
	
	public DataDiferencaDiasMatcher(Integer diferencaDias) {
		this.diferencaDias = diferencaDias;
	}

	@Override
	public void describeTo(Description description) {
		var dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(DataUtils.obterDataComDiferencaDias(diferencaDias));
		String dataExtenso = dataCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diferencaDias));
	}

}
