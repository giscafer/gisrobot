package com.giscafer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.giscafer.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	//消息类型
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
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
		text.setCreateTime(String.valueOf(new Date().getTime()));
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
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
}
