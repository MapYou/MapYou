/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.util.UtilAndroid;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeLayoutHome extends TabActivity {

	private TabHost tabHost;

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_layout);

		tabHost = getTabHost();

		TabSpec mapmeTab = tabHost.newTabSpec("MapMe");
		mapmeTab.setIndicator(UtilAndroid.CURRENT_MAPME.getName(), getResources().getDrawable(R.drawable.icon_mepme_first_tab));
		Intent mapmeTabIntent = new Intent(this, MapMeFirstTab.class);
		mapmeTab.setContent(mapmeTabIntent);

		TabSpec usersTab = tabHost.newTabSpec("Users");        
		usersTab.setIndicator("Users", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
		Intent usersTabIntent = new Intent(this, MapMeSecondTab_User.class);
		usersTab.setContent(usersTabIntent);

		tabHost.addTab(mapmeTab);
		tabHost.addTab(usersTab);
	}




}