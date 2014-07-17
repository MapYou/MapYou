/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
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

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_layout);
		TabHost tabHost = getTabHost();
		
		TabSpec photospec = tabHost.newTabSpec("MapMe");
        photospec.setIndicator("MapMe", getResources().getDrawable(R.drawable.icon_mepme_first_tab));
        Intent photosIntent = new Intent(this, MapMeFirstTab.class);
        photospec.setContent(photosIntent);
         
        TabSpec songspec = tabHost.newTabSpec("Users");        
        songspec.setIndicator("Users", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
        Intent songsIntent = new Intent(this, MapMeSecondTab_User.class);
        songspec.setContent(songsIntent);
         
        tabHost.addTab(photospec);
        tabHost.addTab(songspec);
	}
}
