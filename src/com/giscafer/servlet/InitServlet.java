package com.giscafer.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.giscafer.thread.TokenThread;
import com.giscafer.util.WeixinUtil;

/**
 * ��ʼ��servlet
 * @author giscafer
 *
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	public void init() throws ServletException {
		// ��ȡweb.xml�����õĲ���
		WeixinUtil.APPID = getInitParameter("appid");
		WeixinUtil.APPSECRET = getInitParameter("appsecret");

		log.info("weixin api appid:{}", WeixinUtil.APPID);
		log.info("weixin api appsecret:{}", WeixinUtil.APPSECRET);

		// δ����appid��appsecretʱ������ʾ
		if ("".equals(WeixinUtil.APPID) || "".equals(WeixinUtil.APPSECRET)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// ������ʱ��ȡaccess_token���߳�
			new Thread(new TokenThread()).start();
		}
	}
}