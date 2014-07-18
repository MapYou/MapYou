package it.mapyou.view;
import java.util.List;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 */

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeAdapter extends ArrayAdapter<MapMe>{

	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public MapMeAdapter(Context context, int resource,
			List<MapMe> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.mapme_grid_view, null);
		TextView t = (TextView) convertView.findViewById(R.id.textViewNameMapMeOnGridView);
		MapMe m = getItem(position);
		t.setText(m.getName());
		return convertView;
	}

}
