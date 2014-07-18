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
		
//		Random r = new Random();
//		MapMe mapMe = new MapMe();
//		Route route = new Route();
//		User admin = new User();
//		admin.setNickname("admin");
//		admin.setEmail("admin@gmail.com");
//		StartPoint startPoint= new StartPoint();
//		EndPoint endPoint = new EndPoint();
//		startPoint.setLatitude(r.nextDouble());
//		startPoint.setLongitude(r.nextDouble());
//		endPoint.setLatitude(r.nextDouble());
//		endPoint.setLongitude(r.nextDouble());
//		route.setStartPoint(startPoint);
//		route.setEndPoint(endPoint);
//		mapMe.setRoute(route);
//		mapMe.setName("mapme name");
//		mapMe.setAdministrator(admin);
//		mapMe.setStartAddress("start address");
//		mapMe.setEndAddress("end address");
//		List<Mapping> mps = new ArrayList<Mapping>();
//		for(int i=1; i<6; i++){
//			Mapping m = new Mapping();
//			m.setUser(new User("user_"+i, "email_"+i));
//			Route rr = new Route();
//			StartPoint sp= new StartPoint();
//			EndPoint ep = new EndPoint();
//			sp.setLatitude(r.nextDouble());
//			sp.setLongitude(r.nextDouble());
//			ep.setLatitude(r.nextDouble());
//			ep.setLongitude(r.nextDouble());
//			rr.setStartPoint(sp);
//			rr.setEndPoint(ep);
//			m.setRoute(rr);
//			mps.add(m);
//		}
//		mapMe.setMapping(mps);
		
//		Bundle b = new Bundle();
//		b.putParcelable("mapme", mapMe);
		Bundle bu = getIntent().getBundleExtra("mapme"); 
		TabHost tabHost = getTabHost();
		TabSpec photospec = tabHost.newTabSpec("MapMe");
        photospec.setIndicator("MapMe", getResources().getDrawable(R.drawable.icon_mepme_first_tab));
        Intent photosIntent = new Intent(this, MapMeFirstTab.class);
        photosIntent.putExtras(bu);
        photospec.setContent(photosIntent);
         
        TabSpec songspec = tabHost.newTabSpec("Users");        
        songspec.setIndicator("Users", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
        Intent songsIntent = new Intent(this, MapMeSecondTab_User.class);
        songsIntent.putExtras(bu);
        songspec.setContent(songsIntent);
         
        tabHost.addTab(photospec);
        tabHost.addTab(songspec);
	}
}
