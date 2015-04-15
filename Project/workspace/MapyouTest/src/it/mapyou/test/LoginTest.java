package it.mapyou.test;

import it.mapyou.controller.DeviceController;
import it.mapyou.controller.ParsingController;
import it.mapyou.controller.network.Server;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.model.User;

import java.util.HashMap;

import junit.framework.TestCase;

import org.json.JSONObject;


public class LoginTest extends TestCase {

	private JSONObject obj;
	private String page; 
	private HashMap<String, String> parameter;
	private DeviceController dev;
	private Server ser;
	private ParsingController par;
	private User u;


	protected void setUp() throws Exception {
		
		dev = DeviceController.getInstance();
		parameter = new HashMap<String, String>();
		dev.init();
		ser = dev.getServer();
		par = dev.getParsingController();
		page = SettingsServer.REGISTER_PAGE;
	}
	
	public void test_login(){
		
		parameter.put("nickname", "user");
		parameter.put("password", "g");
		 
		obj=ser.requestJson(page, ser.setParameters(parameter));
		try {
			u = par.getUserParser().parseFromJsonObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("user",u.getNickname());
	}
	
	public void test_registration(){
		
		parameter.put("nickname", "ggkjg");
		parameter.put("email", "ggioo91@hotmail.it");
		char[] ch = new char[26*4];
		for(int i=0; i<ch.length; i++){
			ch[i] = (char)(((i+1)%ch.length/4)+65);
		}
		parameter.put("password", new String(ch));
		String res = ser.request(page, ser.setParameters(parameter));
		assertEquals("-1", res);
	}
}
