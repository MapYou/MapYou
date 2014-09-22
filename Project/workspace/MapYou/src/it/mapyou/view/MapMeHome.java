
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeHome extends MapyouActivity {
	private MapMe mapme;
	private TextView textNickname, textStart, textend;
	private ImageView img;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_first_tab);
		textNickname = (TextView) findViewById(R.id.textViewNickname);
		textStart = (TextView) findViewById(R.id.textViewStartLocation);
		textend = (TextView) findViewById(R.id.textViewEndLocation);
		img=(ImageView) findViewById(R.id.imageView3);
		mapme = UtilAndroid.CURRENT_MAPME;
		textNickname.setText(mapme.getAdministrator().getNickname());
		Segment route = mapme.getSegment();
		Point spp = route.getStartPoint();
		Point ep = route.getEndPoint();
		textend.setText(ep.getLocation());
		textStart.setText(spp.getLocation());


		if(sp.getString("facebook", "")!=""){
			Bitmap b= BitmapParser.getThumbnail(getApplicationContext());
			img.setImageBitmap(b);
		}
		else;

	}

 

	public void live(View v){

		boolean isGPS=false;
		LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(isGPS)
			startActivity(new Intent(this, LiveTabHome.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
		
		else
		{
			alertGPS("GPS disabled", "Do you want enable gps?");
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if(isGPSEnabled || isNetworkEnabled){
				startActivity(new Intent(this, LiveTabHome.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Wait for GPS signal...", 5000);
		}
	}

	public void alertGPS( String title, String message ){

		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle( title )
		.setMessage( message )
		.setCancelable(false)
		.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		})
		.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		}).show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
