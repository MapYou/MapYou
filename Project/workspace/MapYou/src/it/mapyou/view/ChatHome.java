/**
 * 
 */
package it.mapyou.view;

 
import it.mapyou.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatHome extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlayouthome);
	}
	
	public void broadcasr(View v){
		
		Intent i = new Intent(getApplicationContext(), BroadcastChat.class);
		startActivity(i);
		
	}

}
