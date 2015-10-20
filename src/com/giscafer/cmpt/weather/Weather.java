package com.giscafer.cmpt.weather;

import java.util.List;
import java.util.Map;

/**
 * ÌìÆøpojo
 * 
 * @author giscafer ²Î¿¼£ºhttp://apis.baidu.com/heweather/weather/free
 */
public class Weather {
	private Aqi aqi;;
	private Basic basic;
	private DailyForeCast[] daily_forecast;
	private List<Map> hourly_forecast;
	private Map now;
	private String status;
	private Map suggestion;

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public DailyForeCast[] getDaily_forecast() {
		return daily_forecast;
	}

	public void setDaily_forecast(DailyForeCast[] daily_forecast) {
		this.daily_forecast = daily_forecast;
	}

	public Aqi getAqi() {
		return aqi;
	}

	public void setAqi(Aqi aqi) {
		this.aqi = aqi;
	}

	public List<Map> getHourly_forecast() {
		return hourly_forecast;
	}

	public void setHourly_forecast(List<Map> hourly_forecast) {
		this.hourly_forecast = hourly_forecast;
	}

	public Map getNow() {
		return now;
	}

	public void setNow(Map now) {
		this.now = now;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Map suggestion) {
		this.suggestion = suggestion;
	}

}
