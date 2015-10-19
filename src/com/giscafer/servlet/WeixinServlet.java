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
 * 所有菜单请求处理servlet
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
		String signature = req.getParameter("signature");// 微信加密签名
		String timestamp = req.getParameter("timestamp");// 时间戳
		String nonce = req.getParameter("nonce");// 随机数
		String echostr = req.getParameter("echostr");// 随机字符串

		PrintWriter out = resp.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
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
			// 发送方帐号（open_id）
			String fromUserName = map.get("FromUserName");
			// 公众帐号
			String toUserName = map.get("ToUserName");
			// 消息类型
			String MsgType = map.get("MsgType");
			// 消息内容
			String content = map.get("Content");
			String message = null;
			// 文本消息类型的回复
			if (MessageUtil.MESSAGE_TYPE_TEXT.equals(MsgType)) {
				// 天气查询
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.weatherMenu());
				}
				// 公交查询
				else if ("2".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.busQueryMenu());
				}
				// 周边查询
				else if ("3".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.peripheralSearchMenu());
				}
				// 图片消息
				else if ("4".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				}
				// 音乐消息
				else if ("5".equals(content)) {
					message = MessageUtil.initMusicMessage(toUserName, fromUserName);
				}
				// 问号显示主菜单
				else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}
				// 问号显示主菜单
				else if (content.startsWith("翻译")) {
					String word=content.replaceAll("^翻译","").trim();
					if("".equals(word)){
						message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.introBaiduTrans());
					}else{
						message = MessageUtil.initText(toUserName, fromUserName,Components.dictTranslate(word));
					}
				}
				//其他回复时，返回图文消息
				else{
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}

			}// 事件推送 MsgType=event
			else if (MessageUtil.MESSAGE_TYPE_EVENT.equals(MsgType)) {
				// 事件类型
				String eventType = map.get("Event");
				// 订阅
				if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				}// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单CLICK事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				// 自定义菜单VIEW事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					String url=map.get("EventKey");
					message=MessageUtil.initText(toUserName, fromUserName, url);
				}
				// 地理位置定位（有问题）
				/*else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					String Label=map.get("Label");
					String Location_X=map.get("Location_X");
					String Location_Y=map.get("Location_Y");
					String locationInfo="您所在地理位置为："+Label+"，地理坐标经度为："+Location_X+"，纬度为："+Location_Y;
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
