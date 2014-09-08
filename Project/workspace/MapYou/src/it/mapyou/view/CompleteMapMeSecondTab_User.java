/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class CompleteMapMeSecondTab_User extends Activity {

	private MapMe mapme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_second_tab);

		mapme = Util.CURRENT_MAPME;

	}

	@Override
	public void onBackPressed() {
		Intent i= new Intent(this, MapMeLayoutHome.class);
		startActivity(i);
	}

}
