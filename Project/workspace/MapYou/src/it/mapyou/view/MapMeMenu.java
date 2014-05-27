/**
 * 
 */
package it.mapyou.view;

 
import it.mapyou.R;
import it.mapyou.util.UtilAndroid;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeMenu extends Activity {

	private SharedPreferences sp;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_menu);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		UtilAndroid.makeToast(getApplicationContext(), sp.getString("nickname", ""), 6000);
	}
}
