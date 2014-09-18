/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class MapyouActivity extends Activity {
	
	protected SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		sp.edit().putString(UtilAndroid.CURRENT_ACTIVITY_TAG, this.getClass().getName()).commit();
	}
	
	
}
