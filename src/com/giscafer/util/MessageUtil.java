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
	
	//��Ϣ����
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
	 * xmlתΪmap����
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
	 * ���ı���ϢתΪ����XML����
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
	 * ���˵�
	 * @return
	 */
	public static String menuText(){
		StringBuffer b=new StringBuffer();
		b.append("��ӭ��עgisrobot,�밴�ղ˵���ʾ���в�����\n\n");
		b.append("1������Ԥ��\n");
		b.append("2��������ѯ\n");
		b.append("3���ܱ�����\n\n");
		b.append("�ظ���������ʾ�˰����˵���");
		return b.toString();
	}
	/**
	 * ������ѯ
	 * @return String
	 */
	public static String weatherMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("����Ԥ����ѯ���ڿ����С���");
		return sb.toString();
	}
	/**
	 * ������ѯ
	 * @return String
	 */
	public static String busQueryMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("������ѯ���ڿ����С���");
		return sb.toString();
	}
	/**
	 * �ܱ߲�ѯ
	 * @return String
	 */
	public static String peripheralSearchMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("�ܱ��������ڿ����С���");
		return sb.toString();
	}
}
