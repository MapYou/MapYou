package it.mapyou.view;

import it.mapyou.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class NotificationTabHome extends TabActivity {

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_tab_layout);
 
		TabHost tabHost = getTabHost();

		TabSpec first_completeMapMeTab = tabHost.newTabSpec("MapMe");
		first_completeMapMeTab.setIndicator("Notification", getResources().getDrawable(R.drawable.icon_mepme_first_tab));
		Intent mapmeTabIntent = new Intent(this, NotificationList.class);
		first_completeMapMeTab.setContent(mapmeTabIntent);

		TabSpec second_completeMapMeTab = tabHost.newTabSpec("Users");        
		second_completeMapMeTab.setIndicator("Chat notification", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
		Intent usersTabIntent = new Intent(this, NotificationSecondTab.class);
		second_completeMapMeTab.setContent(usersTabIntent);

		tabHost.addTab(first_completeMapMeTab);
		tabHost.addTab(second_completeMapMeTab);
	}	
}