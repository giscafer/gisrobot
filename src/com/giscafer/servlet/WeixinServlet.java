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

public class WeixinServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature=req.getParameter("signature");//΢�ż���ǩ��
		String timestamp=req.getParameter("timestamp");//ʱ���
		String nonce=req.getParameter("nonce");//�����
		String echostr=req.getParameter("echostr");//����ַ���
		
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out=resp.getWriter();
		try {
			Map<String,String> map=MessageUtil.xmlToMap(req);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String MsgType=map.get("MsgType");
			String content=map.get("Content");
			String message=null;
			//�ı���Ϣ����
			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
				//������Ϣ���ͽ��з��ز�ͬ����
				if("1".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.weatherMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.busQueryMenu());
				}else if("3".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.peripheralSearchMenu());
				}else if("?".equals(content) || "��".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				
			}//�¼�
			else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
