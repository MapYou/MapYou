
/**
 * 
 */
package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import it.mapyou.util.BitmapParser;
import it.mapyou.util.UtilAndroid;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeFirstTab extends Activity {
	private MapMe mapme;
	private TextView textNickname, textStart, textend;
	private ImageView img;
	private SharedPreferences sppp;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_first_tab);
		sppp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		textNickname = (TextView) findViewById(R.id.textViewNickname);
		textStart = (TextView) findViewById(R.id.textViewStartLocation);
		textend = (TextView) findViewById(R.id.textViewEndLocation);
		img=(ImageView) findViewById(R.id.imageView3);
		mapme = UtilAndroid.CURRENT_MAPME;
		textNickname.setText(mapme.getAdministrator().getNickname());
		Segment route = mapme.getSegment();
		Point sp = route.getStartPoint();
		Point ep = route.getEndPoint();
		textend.setText(ep.getLocation());
		textStart.setText(sp.getLocation());
		

		if(sppp.getString("facebook", "")!=""){
			Bitmap b= BitmapParser.getThumbnail(getApplicationContext());
			img.setImageBitmap(b);
		}
		else;

	}
	
	 
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}
	
	public void live(View v){
		startActivity(new Intent(this, CompleteMapMeLayoutHome.class));
	}
}
