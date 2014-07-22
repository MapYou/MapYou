package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;

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
			convertView=View.inflate(parent.getContext(), R.layout.mapme_grid_view, null);
		}

		TextView name= (TextView) convertView.findViewById(R.id.textViewNameMapMeOnGridView);
		TextView start= (TextView) convertView.findViewById(R.id.TextViewStartMapme);
		TextView end= (TextView) convertView.findViewById(R.id.TextViewEndMapMe);
		
		MapMe m = mapme.get(position);
		name.setText(m.getName());
		start.setText(m.getStartAddress());
		end.setText(m.getEndAddress());
		 
		return convertView;
	}
	

	

}
