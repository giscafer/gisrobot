package com.giscafer.cmpt.weather;

import java.util.Map;

/**
 * ������Ϣ
 * @author giscafer
 */
public class Basic {

	private String city; 	//��������
	private String cnty;  	//����
	private String id; 		//����ID
	private String lon;		//���о���
	private String lat;		//����ά��
	private Map update;	//����ʱ��   "loc": "2015-07-02 14:44", //����ʱ�� "utc": "2015-07-02 06:46"  //UTCʱ��
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCnty() {
		return cnty;
	}
	public void setCnty(String cnty) {
		this.cnty = cnty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Map getUpdate() {
		return update;
	}
	public void setUpdate(Map update) {
		this.update = update;
	}
	
}
