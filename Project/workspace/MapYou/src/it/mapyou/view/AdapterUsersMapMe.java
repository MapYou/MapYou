package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.Mapping;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterUsersMapMe extends BaseAdapter {

	private List<Mapping> map;

	public AdapterUsersMapMe(List<Mapping> map) {
		this.map = map;
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

	public void addItem(Mapping i){
		map.add(i);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.user_profile_mapme_grid, parent);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNickname);
		TextView e= (TextView) convertView.findViewById(R.id.textViewEmail);
		Mapping m = map.get(position);
		n.setText(m.getUser().getNickname());
		e.setText(m.getUser().getEmail());
		return convertView;
	} 

}

