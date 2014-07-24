package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.MapMe;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class YourMapMeAdapter extends BaseAdapter{


	private List<MapMe> mapme;
	private Activity act;


	public YourMapMeAdapter(Activity act, List<MapMe> allmapme) {
		this.act=act;
		this.mapme=allmapme;


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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.mapme_list_row, null);
		}

		TextView admin = (TextView) convertView.findViewById(R.id.admin);
		TextView numusr = (TextView) convertView.findViewById(R.id.num_users);
		TextView name = (TextView) convertView.findViewById(R.id.mapmename);
		TextView sa = (TextView) convertView.findViewById(R.id.textViewSA);
		TextView ea = (TextView) convertView.findViewById(R.id.textViewEA);

		MapMe m = mapme.get(position);

		admin.setText("Admin: "+m.getAdministrator().getNickname());
		name.setText(m.getName());
		sa.setText(m.getStartAddress());
		ea.setText(m.getEndAddress());
		numusr.setText(String.valueOf("0"+" / "+String.valueOf(m.getMaxNumUsers())));

		return convertView;
	}
}

