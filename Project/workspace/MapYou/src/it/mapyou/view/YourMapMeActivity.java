package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.MapMe;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 
 */

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class YourMapMeActivity extends FragmentActivity {

	private List<MapMe> mapmes;
	private Activity act;
	private GridView grid;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.you_mapme_layout);
		act = this;
		grid = (GridView) findViewById(R.id.gridViewYourMapMe);
		mapmes = getIntent().getExtras().getParcelableArrayList("listmapme");

		MapMeAdapter adpter = new MapMeAdapter(this, R.layout.mapme_grid_view, mapmes);
		grid.setAdapter(adpter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), 
		                "Selezionato "+position, Toast.LENGTH_LONG).show();
				MapMe m = mapmes.get(position);
				Intent i = new Intent(act, MapMeLayoutHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//				Bundle b = new Bundle();
//				b.putParcelable("mapme", m);
//				i.putExtras(b);
				startActivity(i);
			}
		});
	
	}
	
	private void initGrid(){
		
	}
}
