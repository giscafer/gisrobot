package com.giscafer.test;

import com.giscafer.po.AccessToken;
import com.giscafer.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args){
		AccessToken token=WeixinUtil.getAccessToken();
		System.out.println("Ʊ�ݣ�"+token.getToken());
		System.out.println("��Чʱ�䣺"+token.getExpiresIn());
	}
}
