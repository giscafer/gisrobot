package com.giscafer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.giscafer.components.Components;
import com.giscafer.util.CheckUtil;
import com.giscafer.util.MessageUtil;
/**
 * ���в˵�������servlet
 * @author giscafer
 *
 */
public class WeixinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");// ΢�ż���ǩ��
		String timestamp = req.getParameter("timestamp");// ʱ���
		String nonce = req.getParameter("nonce");// �����
		String echostr = req.getParameter("echostr");// ����ַ���

		PrintWriter out = resp.getWriter();
		// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			 // Ĭ�Ϸ��ص��ı���Ϣ����  
            String respContent = "�������쳣�����Ժ��ԣ�"; 
            
			Map<String, String> requestMap = MessageUtil.xmlToMap(req);
			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String MsgType = requestMap.get("MsgType");
			// ��Ϣ����
			String content = requestMap.get("Content");
			String message = null;
			// �ı���Ϣ���͵Ļظ�
			if (MessageUtil.MESSAGE_TYPE_TEXT.equals(MsgType)) {
				// ������ѯ
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.weatherMenu());
				}
				// ������ѯ
				else if ("2".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.busQueryMenu());
				}
				// �ܱ߲�ѯ
				else if ("3".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.peripheralSearchMenu());
				}
				// ͼƬ��Ϣ
				else if ("4".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				}
				// ������Ϣ
				else if ("5".equals(content)) {
					message = MessageUtil.initMusicMessage(toUserName, fromUserName);
				}
				// �ʺ���ʾ���˵�
				else if ("?".equals(content) || "��".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}
				// �ٶȷ���
				else if (content.startsWith("����")) {
					String word=content.replaceAll("^����","").trim();
					if("".equals(word)){
						message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.introBaiduTrans());
					}else{
						message = MessageUtil.initText(toUserName, fromUserName,Components.dictTranslate(word));
					}
				}
				// ������ѯ
				else if (content.contains("����")) {
					String city=content.replaceAll("����","").trim();
					if(city.contains("��")){
						city=city.replaceAll("��", "").trim();
					}
					if("".equals(city)){
						message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.introSearchWeather());
					}else{
						message = Components.searchWeather(toUserName, fromUserName, city);
					}
				}
				//�����ظ�ʱ������ͼ����Ϣ
				else{
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}

			}// �¼����� MsgType=event
			else if (MessageUtil.MESSAGE_TYPE_EVENT.equals(MsgType)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
				}
				// �Զ���˵�CLICK�¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// �¼�KEYֵ���봴���Զ���˵�ʱָ����KEYֵ��Ӧ  
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("weather")) {  
                        message=MessageUtil.initText(toUserName, fromUserName,MessageUtil.introSearchWeather());
                    } else if (eventKey.equals("baidu_trans")) { 
                    	message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.introBaiduTrans());
                    } else if (eventKey.equals("13")) {  
                        respContent = "�ܱ����������У������ڴ���";  
                    } else if (eventKey.equals("14")) {  
                        respContent = "��ʷ�ϵĽ��쿪���У������ڴ���";  
                    } else if (eventKey.equals("song_on_demand")) {  
                        respContent = "�����㲥�����У������ڴ���";  
                        message=MessageUtil.initText(toUserName, fromUserName, respContent);
                    } else if (eventKey.equals("22")) {  
                        respContent = "������Ϸ�����У������ڴ���";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "��Ů��̨�����У������ڴ���";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "����ʶ�𿪷��У������ڴ���";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "������྿����У������ڴ���";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "Q��Ȧ�����У������ڴ���";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "��Ӱ���а񿪷��У������ڴ���";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "��ĬЦ�������У������ڴ���";  
                    }  
				}
				// �Զ���˵�VIEW�¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					
				}
				// ����λ�ö�λ�������⣩
				/*else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					String Label=requestMap.get("Label");
					String Location_X=requestMap.get("Location_X");
					String Location_Y=requestMap.get("Location_Y");
					String locationInfo="�����ڵ���λ��Ϊ��"+Label+"���������꾭��Ϊ��"+Location_X+"��γ��Ϊ��"+Location_Y;
					message=MessageUtil.initText(toUserName, fromUserName, locationInfo);
				}*/
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
