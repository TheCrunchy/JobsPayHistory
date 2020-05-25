package payHistory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class PlayerData {
	private LinkedHashMap<String, String> history = new LinkedHashMap<String, String>();
	private Double PayThisRestart = (double) 0;
	
	public void addToHistory(Double amount) {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
	    String date = simpleDateFormat.format(new Date());
	
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		String numberAsString = decimalFormat.format(amount);
		String tempToRemove = "";
		PayThisRestart += amount;
		if (history.size() == 10) {

				String firstKey = history.keySet().stream().findFirst().get();
			
			history.remove(firstKey);
		}
		history.put(date, numberAsString);
	}
	public LinkedHashMap getHistory() {
		return history;
	}
	public double getAmountEarnt() {
		return PayThisRestart;
	}
}
