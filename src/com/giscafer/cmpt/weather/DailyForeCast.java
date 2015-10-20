package com.giscafer.cmpt.weather;

import java.util.Map;
/**
 * 7天天气预报
 * @author giscafer
 *	
 */
public class DailyForeCast {
	private String date;
	private Map astro; 
	private Map cond; 
	private String hum;
	private String pcpn;
	private String pop;
	private String pres;
	private Map tmp; 
	private String vis;
	private Map wind;
	public Map getAstro() {
		return astro;
	}
	public void setAstro(Map astro) {
		this.astro = astro;
	}
	public Map getCond() {
		return cond;
	}
	public void setCond(Map cond) {
		this.cond = cond;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHum() {
		return hum;
	}
	public void setHum(String hum) {
		this.hum = hum;
	}
	public String getPcpn() {
		return pcpn;
	}
	public void setPcpn(String pcpn) {
		this.pcpn = pcpn;
	}
	public String getPop() {
		return pop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public String getPres() {
		return pres;
	}
	public void setPres(String pres) {
		this.pres = pres;
	}
	public Map getTmp() {
		return tmp;
	}
	public void setTmp(Map tmp) {
		this.tmp = tmp;
	}
	public String getVis() {
		return vis;
	}
	public void setVis(String vis) {
		this.vis = vis;
	}
	public Map getWind() {
		return wind;
	}
	public void setWind(Map wind) {
		this.wind = wind;
	} 
	
}
/*{
    "date": "2015-07-02", //预报日期
    "astro": { //天文数值  
        "sr": "04:50", //日出时间
        "ss": "19:47" //日落时间
    },
    "cond": { //天气状况
        "code_d": "100", //白天天气状况代码，参考http://www.heweather.com/documents/condition-code
        "code_n": "100", //夜间天气状况代码
        "txt_d": "晴", //白天天气状况描述
        "txt_n": "晴" //夜间天气状况描述
    },
    "hum": "14", //相对湿度（%）
    "pcpn": "0.0", //降水量（mm）
    "pop": "0", //降水概率
    "pres": "1003", //气压
    "tmp": { //温度
        "max": "34℃", //最高温度
        "min": "18℃" //最低温度
    },
    "vis": "10", //能见度（km）
    "wind": { //风力风向
        "deg": "339", //风向（360度）
        "dir": "东南风", //风向
        "sc": "3-4", //风力
        "spd": "15" //风速（kmph）
    }
}*/