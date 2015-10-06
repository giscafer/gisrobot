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
		String signature=req.getParameter("signature");//微信加密签名
		String timestamp=req.getParameter("timestamp");//时间戳
		String nonce=req.getParameter("nonce");//随机数
		String echostr=req.getParameter("echostr");//随机字符串
		
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
//		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out=resp.getWriter();
		try {
			Map<String,String> map=MessageUtil.xmlToMap(req);
			String FromUserName=map.get("FromUserName");
			String ToUserName=map.get("ToUserName");
			String MsgType=map.get("MsgType");
			String Content=map.get("Content");
			String message=null;
			if("text".equals(MsgType)){
				TextMessage text=new TextMessage();
				text.setContent("您发送的消息是："+Content);
				text.setFromUserName(ToUserName);
				text.setToUserName(FromUserName);
				text.setCreateTime(String.valueOf(new Date().getTime()));
				text.setMsgType("text");
				message=MessageUtil.textMessageToXml(text);
				System.out.println(message);
			}
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
