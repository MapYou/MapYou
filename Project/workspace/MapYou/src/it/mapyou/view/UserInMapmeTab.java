
package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.model.MapMe;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UserInMapmeTab extends Activity {
	
	
	private GridView gridView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		
		gridView=(GridView) findViewById(R.id.gridViewMapMeUsers);
	}

	
	class DownloadUserInMapMe extends AsyncTask<String, Void, MapMe>{

		 
		@Override
		protected MapMe doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		 
		@Override
		protected void onPostExecute(MapMe result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
