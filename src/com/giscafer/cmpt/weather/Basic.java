package com.giscafer.cmpt.weather;

import java.util.Map;

/**
 * 基本信息
 * @author giscafer
 */
public class Basic {

	private String city; 	//城市名称
	private String cnty;  	//国家
	private String id; 		//城市ID
	private String lon;		//城市经度
	private String lat;		//城市维度
	private Map update;	//更新时间   "loc": "2015-07-02 14:44", //当地时间 "utc": "2015-07-02 06:46"  //UTC时间
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
