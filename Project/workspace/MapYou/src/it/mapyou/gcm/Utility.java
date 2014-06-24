package it.mapyou.gcm;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

public class Utility {


	public static void verificaApiKey(Context t){
		try {
			PackageInfo info = t.getPackageManager().getPackageInfo("com.gcm",PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.v("Stampa", "KeyHash:" + Base64.encodeToString(md.digest(),Base64.DEFAULT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String setParameters(Map<String, String> parameters){
	
		StringBuilder body = new StringBuilder();
		Iterator<Entry<String, String>> iterator= parameters.entrySet().iterator();

		while(iterator.hasNext()){

			Entry<String, String> param= iterator.next();
			body.append(param.getKey()).append('=').append(param.getValue());

			if(iterator.hasNext()){
				body.append('&');
			}
		}
		return body.toString();
		
	}
	
	
	
	

}
