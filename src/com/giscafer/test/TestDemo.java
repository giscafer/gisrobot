package com.giscafer.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.giscafer.util.HttpRequestUtil;
import com.giscafer.util.WeixinUtil;
import com.google.gson.Gson;

public class TestDemo {
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "e5f17c8897cc8efda9e71b46b08cb04e");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	@Test
	public void testWeather(){
		String httpUrl = "http://apis.baidu.com/heweather/weather/free";
		String httpArg = "city=北京";
		String jsonResult = request(httpUrl, httpArg);
		JSONObject josnObject = JSONObject.fromObject(jsonResult.toString());
		List<Map> list=(List<Map>) josnObject.get("HeWeather data service 3.0");
		System.out.println(josnObject);
		System.out.println(list.get(0));
	}

}
