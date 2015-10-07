package com.giscafer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.giscafer.po.TextMessage;
import com.giscafer.util.CheckUtil;
import com.giscafer.util.MessageUtil;

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
			Map<String, String> map = MessageUtil.xmlToMap(req);
			// ���ͷ��ʺţ�open_id��
			String fromUserName = map.get("FromUserName");
			// �����ʺ�
			String toUserName = map.get("ToUserName");
			// ��Ϣ����
			String MsgType = map.get("MsgType");
			// ��Ϣ����
			String content = map.get("Content");
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
				// �ʺ���ʾ���˵�
				else if ("?".equals(content) || "��".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}else{
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}

			}// �¼����� MsgType=event
			else if (MessageUtil.MESSAGE_TYPE_EVENT.equals(MsgType)) {
				// �¼�����
				String eventType = map.get("Event");
				// ����
				if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
				}
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
