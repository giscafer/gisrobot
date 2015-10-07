package com.giscafer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.giscafer.po.Article;
import com.giscafer.po.NewsMessage;
import com.giscafer.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	//消息类型
	public static final String MESSAGE_TYPE_TEXT="text";
	public static final String MESSAGE_TYPE_NEWS="news";
	public static final String MESSAGE_TYPE_IMAGE="image";
	public static final String MESSAGE_TYPE_VOICE="voice";
	public static final String MESSAGE_TYPE_VIDEO="video";
	public static final String MESSAGE_TYPE_LINK="link";
	public static final String MESSAGE_TYPE_LOCATION="location";
	public static final String MESSAGE_TYPE_EVENT="event";
	//关注
	public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
	//点击
	public static final String EVENT_TYPE_CLICK="CLICK";
	public static final String EVENT_TYPE_VIEW="VIEW";
	/**
	 * xml转为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map =new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		
		InputStream ins=request.getInputStream();
		Document doc=reader.read(ins);
		
		Element root =doc.getRootElement();
		
		List<Element> list=root.elements();
		
		for (Element e:list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 将文本信息转为对象XML类型
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text=new TextMessage();
		text.setContent(content);
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
		return MessageUtil.textMessageToXml(text);
	}
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer b=new StringBuffer();
		b.append("欢迎关注gisrobot,请按照菜单提示进行操作：\n\n");
		b.append("1、天气预报\n");
		b.append("2、公交查询\n");
		b.append("3、周边搜索\n\n");
		b.append("回复“？”显示此帮助菜单！");
		return b.toString();
	}
	/**
	 * 天气查询
	 * @return String
	 */
	public static String weatherMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("天气预报查询正在开发中……");
		return sb.toString();
	}
	/**
	 * 公交查询
	 * @return String
	 */
	public static String busQueryMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("公交查询正在开发中……");
		return sb.toString();
	}
	/**
	 * 周边查询
	 * @return String
	 */
	public static String peripheralSearchMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("周边搜索正在开发中……");
		return sb.toString();
	}
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<Article> articleList=new ArrayList<Article>();
		NewsMessage newsMessage=new NewsMessage();
		
		Article article=new Article();
		article.setTitle("GIS520社区介绍");
		article.setDescription("最好的GIS学习交流平台 专注为地理信息科学专业学生提供丰富的gis行业相关书籍、课题研究,gis开发竞赛、地信课件和地信考研资料,以及大量的gis行业软件学习资料");
		article.setPicUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3065324353,1956491651&fm=58&s=C942EC1ACF30548A5AEC78C00300A0B0");
		article.setUrl("http://www.gis520.com/");
		articleList.add(article);
		
		Article article1=new Article();
		article1.setTitle("ArcGIS教程之DEM应用——水文分析");
		article1.setDescription("ArcGIS教程之DEM水文分析详细图文教程，本教程和之前的两个教程有关联的，数据上是使用上一个教程的结果……");
		article1.setPicUrl("http://courses.gis520.com/data/attachment/portal/201505/28/185251kfbsztoap0c0nwx0.jpg.thumb.jpg");
		article1.setUrl("http://www.gis520.com/article-830-1.html");
		articleList.add(article1);
		
		Article article2=new Article();
		article2.setTitle("ArcGIS地形分析--TIN及DEM的生成，TIN的显示");
		article2.setDescription("DEM是对地形地貌的一种离散的数字表达，是对地面特性进行空间描述的一种数字方法、途径，它的应用可遍及整个地学领域");
		article2.setPicUrl("http://courses.gis520.com/data/attachment/portal/201505/28/185251kfbsztoap0c0nwx0.jpg.thumb.jpg");
		article2.setUrl("http://www.gis520.com/article-829-1.html");
		articleList.add(article2);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
		newsMessage.setArticles(articleList);
		newsMessage.setArticleCount(articleList.size());
		message=newsMessageToXml(newsMessage);
		return message;
	}
}
