/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.view.adapter.SettingsAdapter;
import it.mapyou.view.settings.DeleteAccount;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SettingsActivity extends Activity{
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		setTitle("Settings");
		((ListView)findViewById(R.id.listViewSettings)).setAdapter(new SettingsAdapter(this, 
				new DeleteAccount(this, R.layout.delete_account)
		));
	}
}
