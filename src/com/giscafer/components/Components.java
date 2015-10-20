package com.giscafer.components;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.giscafer.cmpt.trans.BaiduTrans;
import com.giscafer.cmpt.trans.Data;
import com.giscafer.cmpt.trans.Parts;
import com.giscafer.cmpt.trans.Symbols;
import com.giscafer.cmpt.weather.Weather;
import com.giscafer.po.Article;
import com.giscafer.po.NewsMessage;
import com.giscafer.util.CharUtil;
import com.giscafer.util.MessageUtil;
import com.giscafer.util.WeixinUtil;

/**
 * ΢�Ź��ܲ����
 * @author giscafer
 *
 */
public class Components {

	/**
	 * �ٶȴʵ䷭��
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dictTranslate(String source) throws UnsupportedEncodingException {
		int resCnt=0;
		StringBuffer dst = new StringBuffer("ԭ�ģ�\n"+source+"\n���ģ�\n");
		String url = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=ARzKx2rdHirLoS9Q2T7yTiXZ&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		Object obj=jsonObject.get("data");
		String errno=jsonObject.getString("errno");
		if("0".equals(errno) && !"[]".equals(obj.toString())){
			BaiduTrans transResult=(BaiduTrans) JSONObject.toBean(jsonObject,BaiduTrans.class); //�����������ĸ����Сд
//			Gson gson=new Gson();
//			BaiduTrans transResult=gson.fromJson(jsonObject.toString();, BaiduTrans.class);//ʹ��google��gsonһ���ﵽЧ������������ĸû��Ҫ��
			Data data=transResult.getData();
			Symbols symbols=data.getSymbols()[0];
			String phzh=symbols.getPh_zh()==null?"":"����ƴ����"+symbols.getPh_zh()+"\n";
			String phen=symbols.getPh_en()==null?"":"Ӣʽ���꣺"+symbols.getPh_en()+"\n";
			String pham=symbols.getPh_am()==null?"":"��ʽ���꣺"+symbols.getPh_am()+"\n";
			dst.append(phen+pham);
			dst.append(phzh+phen+pham);
			
			Parts[] parts=symbols.getParts();
			String pat=null;
			for(Parts part:parts){
				pat=(part.getPart()!=null && !"".equals(part.getPart()))?"["+part.getPart()+"]":"";
				String[] means=part.getMeans();
				dst.append(pat);
				for(String mean:means){
					if(means.length>1){
						resCnt++;
						dst.append(resCnt+"."+mean+"\n");
					}else{
						dst.append(mean+"\n");
					}
					
				}
				
			}
		}else{
			dst.append(bmtTranslate(source));
		}

		return dst.toString();
	}
	/**
	 * �ٶȾ��ӷ���
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
	/**
	 * ����Ԥ���ӿ�
	 * @param urlAll
	 *            :����ӿ�
	 * @param httpArg
	 *            :����
	 * @return ���ؽ��
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
	        // ����apikey��HTTP header
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
	/**
	 * ����Ԥ��ʹ��ͼ��ģ�巵��
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName,List<Article> articleList){
		String message=null;
		NewsMessage newsMessage=new NewsMessage();
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
		newsMessage.setArticles(articleList);
		newsMessage.setArticleCount(articleList.size());
		message=MessageUtil.newsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * ����Ԥ���ӿ�
	 * @param city
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String searchWeather(String toUserName,String fromUserName,String city) throws UnsupportedEncodingException{
		String cityName="";
		String message=null;
		StringBuffer weatherStr=new StringBuffer();
		List<Article> articleList=new ArrayList<Article>();
		if("".equals(city)){
			city="beijing";
		}
		if(CharUtil.isChinese(city)){
			cityName=city;
			city=URLEncoder.encode(city, "UTF-8");
		}
		//
		String httpUrl = "http://apis.baidu.com/heweather/weather/free";
		String httpArg = "city="+city;
		String jsonResult = request(httpUrl, httpArg);
		JSONObject josnObject = JSONObject.fromObject(jsonResult.toString());
		List<Map> list=(List<Map>) josnObject.get("HeWeather data service 3.0");
		Map map=list.get(0);
		String status=((JSONObject) map).getString("status");
		//�����Ƿ�ɹ�
		if("ok".equals(status)){
			
			JSONObject josnObj = JSONObject.fromObject(map.toString());
			Weather weatherResult=(Weather) JSONObject.toBean(josnObj,Weather.class); 
//			System.out.println(weatherResult.getNow());
			//ʵ������
			Map now=weatherResult.getNow();
			StringBuffer sb=new StringBuffer("ʵ��  ");
			//����״��
			JSONObject cond =JSONObject.fromObject(now.get("cond"));
			String condTxt=(String) cond.get("txt");
			sb.append("������"+condTxt);
			String tmp=(String) now.get("tmp");
			sb.append("���¶ȣ�"+tmp+"��");
			//��������
			JSONObject windObj =JSONObject.fromObject(now.get("wind"));
//			System.out.println(windObj);
			String dir=(String) windObj.get("dir"); //����
			String dirStr=dir==null?"":"������"+dir;
			String sc=(String) windObj.get("sc"); //����
			String scStr=sc==null?"":"��������"+sc+"��";
			String spd=(String) windObj.get("spd"); ///���٣�kmph��
			String spdStr=spd==null?"":"�����٣�"+spd+"kmph";
			sb.append(dirStr+scStr+spdStr);
			
			System.out.println(sb.toString());
			Article nowNews=new Article();
			nowNews.setTitle(cityName+"����Ԥ��");
			nowNews.setDescription(cityName+"����Ԥ��");
			nowNews.setPicUrl("");
			nowNews.setUrl("");
			articleList.add(nowNews);
			
			Article nowNews1=new Article();
			nowNews1.setTitle(sb.toString());
			nowNews1.setDescription(sb.toString());
			nowNews1.setPicUrl("");
			nowNews1.setUrl("");
			articleList.add(nowNews1);
			
		}else{
			weatherStr.append("������ѯʧ�ܣ����Ժ����ԣ�");
		}
		message=initNewsMessage(toUserName,fromUserName,articleList);
		return message;
	}
}
