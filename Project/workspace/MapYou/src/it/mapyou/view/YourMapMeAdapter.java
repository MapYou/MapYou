package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.util.BitmapParser;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class YourMapMeAdapter extends BaseAdapter{


	private List<MapMe> mapme;
	private Activity act;
	private MapMe m;
	private SharedPreferences sp;

	public YourMapMeAdapter(Activity act, List<MapMe> allmapme) {
		this.act=act;
		this.mapme=allmapme;
		sp=PreferenceManager.getDefaultSharedPreferences(act);


	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapme.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mapme.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.mapme_list_row, null);
		}

		TextView admin = (TextView) convertView.findViewById(R.id.admin);
		TextView numusr = (TextView) convertView.findViewById(R.id.num_users);
		TextView name = (TextView) convertView.findViewById(R.id.mapmename);
		TextView sa = (TextView) convertView.findViewById(R.id.textViewSA);
		TextView ea = (TextView) convertView.findViewById(R.id.textViewEA);
		ImageView icon=(ImageView) convertView.findViewById(R.id.admin_image);


		m = mapme.get(position);

		if(sp.getString("facebook", "")!=""){
			Bitmap b= BitmapParser.getThumbnail(act.getApplicationContext());
			icon.setImageBitmap(b);
		}
		else;

		admin.setText("Admin: "+m.getAdministrator().getNickname());
		name.setText(m.getName());
		sa.setText(m.getSegment().getStartPoint().getLocation());
		ea.setText(m.getSegment().getEndPoint().getLocation());
		numusr.setText(String.valueOf(
				String.valueOf(m.getNumUsers())+" / "+String.valueOf(m.getMaxNumUsers())));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Util.CURRENT_MAPME = mapme.get(position);
				Intent i = new Intent(act, MapMeLayoutHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				act.startActivity(i);	
			}
		});
		return convertView;
	}
}

