/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.Notification;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NotificationList extends Activity{


	private ListView listView;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_list);
		listView = (ListView)findViewById(R.layout.notification_list);
		int idnot = -1;
		boolean v = false;
		Bundle b = getIntent().getExtras();
		if(b!=null){
			idnot = b.getInt("idnot");
			if(idnot>0)
				v = true;
			else v = false;
		}else v=false;
		
		if(v){
			dispatchNotification(idnot);
		}else{
			retrieveAllPendingNotification();
		}
	}
	
	private void retrieveAllPendingNotification(){
		
	}
	
	private void dispatchNotification(int id){
		Intent i = new Intent(this, NotificationActivity.class);
		Bundle b = new Bundle();
		b.putInt("notification_id", id);
		startActivity(i);
	}
	
}
