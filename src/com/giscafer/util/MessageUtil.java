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
import com.giscafer.po.Image;
import com.giscafer.po.ImageMessage;
import com.giscafer.po.Music;
import com.giscafer.po.MusicMessage;
import com.giscafer.po.NewsMessage;
import com.giscafer.po.TextMessage;
import com.giscafer.test.WeixinTest;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	//��Ϣ����
	public static final String MESSAGE_TYPE_TEXT="text";
	public static final String MESSAGE_TYPE_NEWS="news";
	public static final String MESSAGE_TYPE_IMAGE="image";
	public static final String MESSAGE_TYPE_VOICE="voice";
	public static final String MESSAGE_TYPE_MUSIC="music";
	public static final String MESSAGE_TYPE_VIDEO="video";
	public static final String MESSAGE_TYPE_LINK="link";
//	public static final String MESSAGE_TYPE_LOCATION="location";
	public static final String MESSAGE_TYPE_EVENT="event";
	//��ע
	public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
	//���
	public static final String EVENT_TYPE_CLICK="CLICK";
	public static final String EVENT_TYPE_VIEW="VIEW";
	//����λ��ѡ��
	public static final String EVENT_TYPE_LOCATION="location_select";
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
		text.setCreateTime(new Date().getTime());
		text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
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
		b.append("3���ܱ�����\n");
		b.append("4��ͼƬ��Ϣ\n");
		b.append("5��������Ϣ\n");
		b.append("������ͼ����Ϣ\n\n");
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
	/**
	 * ͼ����ϢתΪxml
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
	 * ͼ����Ϣ����װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<Article> articleList=new ArrayList<Article>();
		NewsMessage newsMessage=new NewsMessage();
		
		Article article=new Article();
		article.setTitle("GIS520��������");
		article.setDescription("��õ�GISѧϰ����ƽ̨ רעΪ������Ϣ��ѧרҵѧ���ṩ�ḻ��gis��ҵ����鼮�������о�,gis�������������ſμ��͵��ſ�������,�Լ�������gis��ҵ���ѧϰ����");
		article.setPicUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3065324353,1956491651&fm=58&s=C942EC1ACF30548A5AEC78C00300A0B0");
		article.setUrl("http://www.gis520.com/");
		articleList.add(article);
		
		Article article1=new Article();
		article1.setTitle("ArcGIS�̳�֮DEMӦ�á���ˮ�ķ���");
		article1.setDescription("ArcGIS�̳�֮DEMˮ�ķ�����ϸͼ�Ľ̳̣����̳̺�֮ǰ�������̳��й����ģ���������ʹ����һ���̵̳Ľ������");
		article1.setPicUrl("http://courses.gis520.com/data/attachment/portal/201505/28/185251kfbsztoap0c0nwx0.jpg.thumb.jpg");
		article1.setUrl("http://www.gis520.com/article-830-1.html");
		articleList.add(article1);
		
		Article article2=new Article();
		article2.setTitle("ArcGIS���η���--TIN��DEM�����ɣ�TIN����ʾ");
		article2.setDescription("DEM�ǶԵ��ε�ò��һ����ɢ�����ֱ��ǶԵ������Խ��пռ�������һ�����ַ�����;��������Ӧ�ÿɱ鼰������ѧ����");
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
	/**
	 * ͼƬ��Ϣ����תΪxml
	 * @param newsMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * ͼƬ��Ϣ��װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		//ͨ���ϴ�ͼƬ���ص�mediaId
		//���1M
		/*String filePath="G:/Java/qrcode.png";
		try {
			String mediaId=WeixinUtil.upload(filePath, token.getToken(), "image");
			System.out.println(mediaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		image.setMediaId("78tJeKk89Vvj3djIx5HVzSlthQXvV7axG9xNk9xlfiW4DNr5aidc7HINmV6X2UEp");
		ImageMessage imageMessage=new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_TYPE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message=imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * ���ֶ���תΪxml
	 * @param newsMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * ������Ϣ��װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();
		//ͨ���ϴ�ͼƬ���ص�mediaId
		//���60kb
		/*String filePath="G:/Java/50.6kb.png";
		try {
			String mediaId=WeixinUtil.upload(filePath, token.getToken(), "thumb");
			System.out.println(mediaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		String rootPath=MessageUtil.class.getResource("/").toString().replace("WEB-INF/classes/","");
		music.setThumbMediaId("c5Yg1lbGH0cYOvvjNHgOHVaZiBffH1QRZln58VJJqETGhilFGf2jR7cA5BWPoV2w");
		music.setTitle("see you again");
		music.setDescription("�ٶ��뼤��7Ƭβ��");
		music.setMusicUrl("http://giscafer.tunnel.mobi/gisrobot/resource/See You Again.mp3");
		music.setHQMusicUrl("http://giscafer.tunnel.mobi/gisrobot/resource/See You Again.mp3");
		MusicMessage musicMessage=new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_TYPE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message=musicMessageToXml(musicMessage);
		return message;
	}
	/**
	 * �ٶȷ���ʹ�ý���
	 * @return
	 */
	public static String introBaiduTrans(){
		StringBuffer sb=new StringBuffer();
		sb.append("������ʹ��ָ�ϡ�\n\n");
		sb.append("1������ʾ��:\n");
		sb.append("��������\n");
		sb.append("����football\n");
		sb.append("2������ʾ��:\n");
		sb.append("��������һ������\n");
		sb.append("����I have a dream\n\n");
		sb.append("�ظ���?����ʾ���˵���");
		return sb.toString();
		
	}
}
