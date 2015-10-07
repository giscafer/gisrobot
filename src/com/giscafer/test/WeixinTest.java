package com.giscafer.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import com.giscafer.po.AccessToken;
import com.giscafer.util.HttpRequestUtil;
import com.giscafer.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args){
		AccessToken token=WeixinUtil.getAccessToken();
		/*System.out.println("票据："+token.getToken());
		System.out.println("有效时间："+token.getExpiresIn());
		System.out.println(WeixinTest.class.getResource("/").toString().replace("WEB-INF/classes/",""));
		String filePath="G:/Java/50.6kb.png";
		try {
			String mediaId=WeixinUtil.upload(filePath, token.getToken(), "thumb");
			System.out.println(mediaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
//		int result=WeixinUtil.createMenu(token.getToken(), menu);
//		int result=WeixinUtil.deleteMenu(token.getToken());
/*		if(result==0){
			System.out.println("创建菜单成功！");
		}else{
			System.out.println("操作失败errcode:"+result);
		}*/
		/*
		 * 测试HttpRequestUtil.httpRequest
		 * String url=WeixinUtil.QUERY_MENU_URL.replace("ACCESS_TOKEN", token.getToken());
		JSONObject jsonObject=HttpRequestUtil.httpRequest(url,"GET",null);
		System.out.println(jsonObject);*/
	}
}
