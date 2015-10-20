package com.giscafer.cmpt.weather;

import java.util.Map;
/**
 * 7������Ԥ��
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
    "date": "2015-07-02", //Ԥ������
    "astro": { //������ֵ  
        "sr": "04:50", //�ճ�ʱ��
        "ss": "19:47" //����ʱ��
    },
    "cond": { //����״��
        "code_d": "100", //��������״�����룬�ο�http://www.heweather.com/documents/condition-code
        "code_n": "100", //ҹ������״������
        "txt_d": "��", //��������״������
        "txt_n": "��" //ҹ������״������
    },
    "hum": "14", //���ʪ�ȣ�%��
    "pcpn": "0.0", //��ˮ����mm��
    "pop": "0", //��ˮ����
    "pres": "1003", //��ѹ
    "tmp": { //�¶�
        "max": "34��", //����¶�
        "min": "18��" //����¶�
    },
    "vis": "10", //�ܼ��ȣ�km��
    "wind": { //��������
        "deg": "339", //����360�ȣ�
        "dir": "���Ϸ�", //����
        "sc": "3-4", //����
        "spd": "15" //���٣�kmph��
    }
}*/