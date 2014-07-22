/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;

import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import it.mapyou.model.Route;
import it.mapyou.model.StartPoint;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 
		
		Bundle bbb = new Bundle();
		bbb.putParcelable("mapme", OnClickMapMe.getMapme());
//		bbb.putParcelable("mapme", mapMe);
	
	//	Bundle bbb = getIntent().getExtras();
 
		Bundle bund = getIntent().getExtras();
		MapMe mapMe = (MapMe) (bund==null?null:bund.isEmpty()?null:bund.getParcelable("mapme"));
		if(bund==null)
			bund = new Bundle();
		else;
		if(mapMe==null){
			Random r = new Random();
			mapMe = new MapMe();
			Route route = new Route();
			User admin = new User();
			admin.setNickname("admin");
			admin.setEmail("admin@gmail.com");
			StartPoint startPoint= new StartPoint();
			EndPoint endPoint = new EndPoint();
			startPoint.setLatitude(45.4640704);
			startPoint.setLongitude(9.1719064);
			endPoint.setLatitude(45.070139);
			endPoint.setLongitude(7.6700892);
			route.setStartPoint(startPoint);
			route.setEndPoint(endPoint);
			mapMe.setRoute(route);
			mapMe.setName("mapme name");
			mapMe.setAdministrator(admin);
			mapMe.setStartAddress("start address");
			mapMe.setEndAddress("end address");
			List<Mapping> mps = new ArrayList<Mapping>();
			for(int i=1; i<6; i++){
				//			for(int k=1; k<7; k++){
				User uu = new User("user_"+i, "email_"+i);
				Mapping m = new Mapping();
				m.setUser(uu);
//				m.setLatitude(41.1306522);
//				m.setLongitude(14.7774233);
				m.setLatitude(45.4640704);
				m.setLongitude(9.1719064);
				mps.add(m);

				Mapping m2 = new Mapping();
				m2.setUser(uu);
//				m2.setLatitude(41.1314723);
//				m2.setLongitude(14.7747089);
				m2.setLatitude(45.070139);
				m2.setLongitude(7.6700892);
				mps.add(m2);
				//			}
			}
			mapMe.setMapping(mps);
			bund.putParcelable("mapme", mapMe);
		}else;

 
		TabHost tabHost = getTabHost();
		TabSpec photospec = tabHost.newTabSpec("MapMe");
		photospec.setIndicator("MapMe", getResources().getDrawable(R.drawable.icon_mepme_first_tab));
		Intent photosIntent = new Intent(this, MapMeFirstTab.class);
		photosIntent.putExtras(bund);
		photospec.setContent(photosIntent);

		TabSpec songspec = tabHost.newTabSpec("Users");        
		songspec.setIndicator("Users", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
		Intent songsIntent = new Intent(this, MapMeSecondTab_User.class);
		songsIntent.putExtras(bund);
		songspec.setContent(songsIntent);

		tabHost.addTab(photospec);
		tabHost.addTab(songspec);
	}
}
