package com.giscafer.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.giscafer.thread.TokenThread;
import com.giscafer.util.WeixinUtil;

/**
 * 初始化servlet
 * @author giscafer
 *
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	public void init() throws ServletException {
		// 获取web.xml中配置的参数
		WeixinUtil.APPID = getInitParameter("appid");
		WeixinUtil.APPSECRET = getInitParameter("appsecret");

		log.info("weixin api appid:{}", WeixinUtil.APPID);
		log.info("weixin api appsecret:{}", WeixinUtil.APPSECRET);

		// 未配置appid、appsecret时给出提示
		if ("".equals(WeixinUtil.APPID) || "".equals(WeixinUtil.APPSECRET)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// 启动定时获取access_token的线程
			new Thread(new TokenThread()).start();
		}
	}
}