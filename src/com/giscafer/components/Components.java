package com.giscafer.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.giscafer.trans.BaiduTrans;
import com.giscafer.trans.Data;
import com.giscafer.trans.Parts;
import com.giscafer.trans.Symbols;
import com.giscafer.util.WeixinUtil;
import com.google.gson.Gson;

/**
 * 微信功能插件类
 * @author giscafer
 *
 */
public class Components {

	/**
	 * 百度词典翻译
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dictTranslate(String source) throws UnsupportedEncodingException {
		int resCnt=0;
		StringBuffer dst = new StringBuffer("["+source+"]翻译结果：\n");
		String url = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=ARzKx2rdHirLoS9Q2T7yTiXZ&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		Object obj=jsonObject.get("data");
		String errno=jsonObject.getString("errno");
		if("0".equals(errno) && !"[]".equals(obj.toString())){
			BaiduTrans transResult=(BaiduTrans) JSONObject.toBean(jsonObject,BaiduTrans.class); //类的属性首字母必须小写
//			Gson gson=new Gson();
//			BaiduTrans transResult=gson.fromJson(jsonObject.toString();, BaiduTrans.class);//使用google的gson一样达到效果，并且首字母没有要求
			Data data=transResult.getData();
			Symbols symbols=data.getSymbols()[0];
			String phzh=symbols.getPh_zh()==null?"":"中文拼音："+symbols.getPh_zh()+"\n";
			String phen=symbols.getPh_en()==null?"":"英式音标："+symbols.getPh_en()+"\n";
			String pham=symbols.getPh_am()==null?"":"美式音标："+symbols.getPh_am()+"\n";
			dst.append(phen+pham);
			dst.append(phzh+phen+pham);
			
			Parts[] parts=symbols.getParts();
			String pat=null;
			for(Parts part:parts){
				pat=(part.getPart()!=null && !"".equals(part.getPart()))?"["+part.getPart()+"]":"";
				String[] means=part.getMeans();
				dst.append(pat);
				for(String mean:means){
					resCnt++;
					dst.append(resCnt+"."+mean+"\n");
				}
				
			}
		}else{
			dst.append(bmtTranslate(source));
		}

		return dst.toString();
	}
	/**
	 * 百度句子翻译
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String bmtTranslate(String source) throws UnsupportedEncodingException{
		StringBuffer dst = new StringBuffer();
		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=ARzKx2rdHirLoS9Q2T7yTiXZ&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		List<Map> list=(List<Map>) jsonObject.get("trans_result");
		for(Map map:list){
			dst.append(map.get("dst"));
		}
		return dst.toString();
	}
}
