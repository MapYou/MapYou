/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.model.NavDrawerItem;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;

	public DrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {       
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.custom_drawer, null);
		}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageView1);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.textView1);
		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
		txtTitle.setText(navDrawerItems.get(position).getTitle());
		return convertView;
	}

}
