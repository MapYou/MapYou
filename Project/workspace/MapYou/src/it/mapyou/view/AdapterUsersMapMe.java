package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MappingUser;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterUsersMapMe extends BaseAdapter {

	private List<MappingUser> map;
	private Context cont;
	private SharedPreferences sp;
	private String admin="";

	public AdapterUsersMapMe(Context cont, List<MappingUser> map) {
		this.cont = cont;
		this.map = map;
		sp=PreferenceManager.getDefaultSharedPreferences(cont.getApplicationContext());
		admin=sp.getString("nickname", "");
	}
	@Override
	public int getCount() {
		return map.size();
	}

	@Override
	public Object getItem(int position) {
		return map.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	public void addItem(MappingUser i){
		map.add(i);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView=View.inflate(cont, R.layout.user_profile_mapme_grid, null);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNickname);
		ImageView icon=(ImageView) convertView.findViewById(R.id.imageView1);

		MappingUser m = map.get(position);

		if(m.getUser().getNickname().equalsIgnoreCase(admin)){
			icon.setImageResource(R.drawable.admin);
			n.setText("Admin:\n"+m.getUser().getNickname());
		}else
			n.setText(m.getUser().getNickname());
		return convertView;
	} 

}

