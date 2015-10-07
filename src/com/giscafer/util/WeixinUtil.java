package com.giscafer.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.giscafer.menu.Button;
import com.giscafer.menu.ClickButton;
import com.giscafer.menu.Menu;
import com.giscafer.menu.ViewButton;
import com.giscafer.po.AccessToken;
/**
 * ���ں�ͨ�ýӿڹ�����
 * @author giscafer
 *	@date 2015-10-08
 */
public class WeixinUtil {
	// �������û�Ψһƾ֤
	public static String APPID="wx27fe66a6820ba9ae";
	// �������û�Ψһƾ֤��Կ
	public static String APPSECRET="973b8f9a2d1d7491a6bd7235e5d9bd31";
	//��ȡACCESS_TOKEN
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//�����ϴ�
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//�˵�����
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//�˵���ѯ
	public static final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//�˵�ɾ��
	private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * get����ӿ�
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		JSONObject jsonObject=null;
		
		try {
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result=EntityUtils.toString(entity,"UTF-8");
				jsonObject=JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * post����ӿ�
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		JSONObject jsonObject=null;
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response=httpClient.execute(httpPost);
			String result=EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSONObject.fromObject(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * ��ȡaccess_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token=new AccessToken();
		String url=ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject=doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	/**
	 * �ļ��ϴ�
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//�����ͷ
		out.write(head);

		//�ļ����Ĳ���
		//���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		//ת��Ϊjson����
		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	/**
	 * ��ʼ���˵�
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		/**�������ֲ˵�*/
		Button lifeAssisBtn=new Button();
		lifeAssisBtn.setType("click");
		lifeAssisBtn.setName("��������");
		//����Ԥ���Ӳ˵�
		ClickButton weatherBtn=new ClickButton();
		weatherBtn.setName("����Ԥ��");
		weatherBtn.setType("click");
		weatherBtn.setKey("weather");
		
		lifeAssisBtn.setSub_button(new Button[]{weatherBtn});
		/**������վ�˵�*/
		Button leisureInnBtn=new Button();
		leisureInnBtn.setName("������վ");
		leisureInnBtn.setType("click");
		// ����Ԥ���Ӳ˵�
		ClickButton musicBtn = new ClickButton();
		musicBtn.setName("�����㲥");
		musicBtn.setType("click");
		musicBtn.setKey("song_on_demand");
		leisureInnBtn.setSub_button(new Button[]{musicBtn});
		//�ٶ�����
		ViewButton searchBtn = new ViewButton();
		searchBtn.setName("����");
		searchBtn.setType("view");
		searchBtn.setUrl("http://www.baidu.com/");
		//ɨ���ά��
		ClickButton scancodeBtn=new ClickButton();
		scancodeBtn.setName("ɨһɨ");
		scancodeBtn.setType("scancode_push");
		scancodeBtn.setKey("scancode_push");
		//����λ��
		ClickButton locationBtn=new ClickButton();
		locationBtn.setName("����λ��");
		locationBtn.setType("location_select");
		locationBtn.setKey("location_select");
		
		
		/**���ù��߲˵�*/
		Button commonToolsBtn=new Button();
		commonToolsBtn.setType("click");
		commonToolsBtn.setName("���ù���");
		commonToolsBtn.setSub_button(new Button[]{scancodeBtn,locationBtn,searchBtn});
		
		menu.setButton(new Button[]{lifeAssisBtn,leisureInnBtn,commonToolsBtn});
		return menu;
	}
	/**
	 * �����˵�
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token,String menu){
		int result=0;
		String url=CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doPostStr(url,menu);
		if(jsonObject!=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
	/**
	 * ɾ���˵�
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int deleteMenu(String token){
		int result=0;
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		if(jsonObject!=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
	public static JSONObject queryMenu(String token){
		String url=QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		return jsonObject;
	}
}
