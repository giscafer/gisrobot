package com.giscafer.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giscafer.po.AccessToken;
import com.giscafer.util.WeixinUtil;

/**
 * ��ʱ��ȡ΢��access_token���߳�
 * @author giscafer
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	
	public static AccessToken accessToken = null;

	public void run() {
		//��ѭ����ʱ��ȡaccess_token����֤����ʧЧ
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken();
				if (null != accessToken) {
					log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}", accessToken.getExpiresIn(), accessToken.getToken());
					// ����7000��
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// ���access_tokenΪnull��60����ٻ�ȡ
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