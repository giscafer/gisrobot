package com.giscafer.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giscafer.po.AccessToken;
import com.giscafer.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * @author giscafer
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	
	public static AccessToken accessToken = null;

	public void run() {
		//死循环定时获取access_token，保证永不失效
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken();
				if (null != accessToken) {
					log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}