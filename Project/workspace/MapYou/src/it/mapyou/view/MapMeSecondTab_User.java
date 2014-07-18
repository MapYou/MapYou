/**
 * 
 */
package it.mapyou.view;

import java.util.ArrayList;
import java.util.List;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import it.mapyou.model.User;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeSecondTab_User extends Activity {

	private MapMe mapme;
	private List<Mapping> mapping;
	private Dialog dialog;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		GridView gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		mapme = (MapMe) getIntent().getExtras().get("mapme");
		mapping = mapme.getMapping();
		UserAdapter adpter = new UserAdapter(this, R.layout.user_profile_mapme_grid, mapping);
		gridview.setAdapter(adpter);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.user_on_mapme_layout);
		Button but  =(Button) dialog.findViewById(R.id.buttonDismissUserOnMapMe);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Mapping m = mapping.get(position);
				User u = m.getUser();
				dialog.setTitle(u.getNickname());
				dialog.show();
			}
		});
	}
}
