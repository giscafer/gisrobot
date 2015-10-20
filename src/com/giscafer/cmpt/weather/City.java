package com.giscafer.cmpt.weather;
/**
 * 空气质量，仅限国内部分城市，国际城市无此字段
 * @author giscafer
 *			"aqi": "30", //空气质量指数
            "co": "0", //一氧化碳1小时平均值(ug/m³)
            "no2": "10", //二氧化氮1小时平均值(ug/m³)
            "o3": "94", //臭氧1小时平均值(ug/m³)
            "pm10": "10", //PM10 1小时平均值(ug/m³)
            "pm25": "7", //PM2.5 1小时平均值(ug/m³)
            "qlty": "优", //空气质量类别
            "so2": "3" //二氧化硫1小时平均值(ug/m³)
 */
public class City {
	private String aqi; 
	private String co;
	private String no2;
	private String o3;
	private String pm10;
	private String pm25;
	private String qlty;
	private String so2;
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getNo2() {
		return no2;
	}
	public void setNo2(String no2) {
		this.no2 = no2;
	}
	public String getO3() {
		return o3;
	}
	public void setO3(String o3) {
		this.o3 = o3;
	}
	public String getPm10() {
		return pm10;
	}
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getQlty() {
		return qlty;
	}
	public void setQlty(String qlty) {
		this.qlty = qlty;
	}
	public String getSo2() {
		return so2;
	}
	public void setSo2(String so2) {
		this.so2 = so2;
	}
	
	
}
