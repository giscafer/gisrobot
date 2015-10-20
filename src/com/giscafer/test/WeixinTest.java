package com.giscafer.test;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.giscafer.components.Components;
import com.giscafer.po.AccessToken;
import com.giscafer.util.WeixinUtil;

public class WeixinTest {

	@Test
	public void baiduTransTest01() {
		String result;
		try {
			result = Components.dictTranslate("�й�����");
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void baiduTransTest02() {
		String result;
		try {
			result = Components.dictTranslate("�ú�ѧϰ����������");
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void initMenu() {
		AccessToken token=WeixinUtil.getAccessToken();
		String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result1=WeixinUtil.createMenu(token.getToken(), menu);
		if(result1==0){
			System.out.println("�����˵��ɹ���");
		}else{
			System.out.println("����ʧ��errcode:"+result1);
		}
	}
	@Test
	public void deleteMenu() {
		AccessToken token=WeixinUtil.getAccessToken();
		int result=WeixinUtil.deleteMenu(token.getToken());
		if(result==0){
			System.out.println("ɾ���˵��ɹ���");
		}else{
			System.out.println("����ʧ��errcode:"+result);
		}
	}
	@Test
	public void testWeather() throws UnsupportedEncodingException {
		Components.searchWeather("","","����");
		Components.searchWeather("","","beijing");
	}

}
