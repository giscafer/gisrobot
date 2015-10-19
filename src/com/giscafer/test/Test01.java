package com.giscafer.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.giscafer.components.Components;

public class Test01 {

	@Test
	public void baiduTransTest01() {
		String result;
		try {
			result = Components.dictTranslate("中国足球");
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
			result = Components.dictTranslate("好好学习，天天向上");
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
