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
public class CompleteMapMeLayoutHome extends TabActivity {

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_layout);
 
		TabHost tabHost = getTabHost();
		
		TabSpec first_completeMapMeTab = tabHost.newTabSpec("MapMe");
		first_completeMapMeTab.setIndicator(Util.CURRENT_MAPME.getName(), getResources().getDrawable(R.drawable.icon_mepme_first_tab));
		Intent mapmeTabIntent = new Intent(this, CompleteMapMeFirstTab.class);
		first_completeMapMeTab.setContent(mapmeTabIntent);

		TabSpec second_completeMapMeTab = tabHost.newTabSpec("Users");        
		second_completeMapMeTab.setIndicator("Options", getResources().getDrawable(R.drawable.icon_mapme_second_tab));
		Intent usersTabIntent = new Intent(this, CompleteMapMeSecondTab_User.class);
		second_completeMapMeTab.setContent(usersTabIntent);

		tabHost.addTab(first_completeMapMeTab);
		tabHost.addTab(second_completeMapMeTab);
	}
}




//bbb.putParcelable("mapme", mapMe);

//	Bundle bbb = getIntent().getExtras();

//Bundle bund = getIntent().getExtras();
//MapMe mapMe = (MapMe) (bund==null?null:bund.isEmpty()?null:bund.getParcelable("mapme"));
//if(bund==null)
//	bund = new Bundle();
//else;
//if(mapMe==null){
//	Random r = new Random();
//	mapMe = new MapMe();
//	Route route = new Route();
//	User admin = new User();
//	admin.setNickname("admin");
//	admin.setEmail("admin@gmail.com");
//	StartPoint startPoint= new StartPoint();
//	EndPoint endPoint = new EndPoint();
//	startPoint.setLatitude(45.4640704);
//	startPoint.setLongitude(9.1719064);
//	endPoint.setLatitude(45.070139);
//	endPoint.setLongitude(7.6700892);
//	route.setStartPoint(startPoint);
//	route.setEndPoint(endPoint);
//	mapMe.setRoute(route);
//	mapMe.setName("mapme name");
//	mapMe.setAdministrator(admin);
//	mapMe.setStartAddress("start address");
//	mapMe.setEndAddress("end address");
//	List<Mapping> mps = new ArrayList<Mapping>();
//	for(int i=1; i<6; i++){
//		//			for(int k=1; k<7; k++){
//		User uu = new User("user_"+i, "email_"+i);
//		Mapping m = new Mapping();
//		m.setUser(uu);
////		m.setLatitude(41.1306522);
////		m.setLongitude(14.7774233);
//		m.setLatitude(45.4640704);
//		m.setLongitude(9.1719064);
//		mps.add(m);
//
//		Mapping m2 = new Mapping();
//		m2.setUser(uu);
////		m2.setLatitude(41.1314723);
////		m2.setLongitude(14.7747089);
//		m2.setLatitude(45.070139);
//		m2.setLongitude(7.6700892);
//		mps.add(m2);
//		//			}
//	}
//	mapMe.setMapping(mps);
//	bund.putParcelable("mapme", mapMe);
//}else;