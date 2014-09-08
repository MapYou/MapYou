/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class BroadcastChat extends Activity {
	
	private String[] users;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.broadcastchat);
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("users")){
			users = b.getStringArray("users");
			if(users!=null){
				
			}
		}
	}

}
