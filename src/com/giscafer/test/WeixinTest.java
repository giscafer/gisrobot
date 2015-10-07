package com.giscafer.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import com.giscafer.po.AccessToken;
import com.giscafer.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args){
		AccessToken token=WeixinUtil.getAccessToken();
		System.out.println("票据："+token.getToken());
		System.out.println("有效时间："+token.getExpiresIn());
		
		String filePath="G:/Java/qrcode.png";
		try {
			String mediaId=WeixinUtil.upload(filePath, token.getToken(), "image");
			System.out.println(mediaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
