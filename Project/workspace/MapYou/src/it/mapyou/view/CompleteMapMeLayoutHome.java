/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class CompleteMapMeLayoutHome extends TabActivity {

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */

	private 	TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(Util.CURRENT_MAPME.getName());
		setContentView(R.layout.complete_mapme_layout);




		Bundle bund = new Bundle();
		bund.putParcelable("mapme", Util.CURRENT_MAPME);

		tabHost = getTabHost();

		TabSpec first_completeMapMeTab = tabHost.newTabSpec("MapMe");
		first_completeMapMeTab.setIndicator("Live", getResources().getDrawable(R.drawable.icon_mepme_first_tab));
		Intent mapmeTabIntent = new Intent(this, CompleteMapMeFirstTab.class);
		first_completeMapMeTab.setContent(mapmeTabIntent);

		TabSpec second_completeMapMeTab = tabHost.newTabSpec("Users");        
		second_completeMapMeTab.setIndicator("Chat", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
		Intent usersTabIntent = new Intent(this, ChatHome.class);
		second_completeMapMeTab.setContent(usersTabIntent);

		tabHost.addTab(first_completeMapMeTab);
		tabHost.addTab(second_completeMapMeTab);
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		menu.clear();
		inflater.inflate(R.menu.menunotification, menu);
		return super.onPrepareOptionsMenu(menu);
	}






}
