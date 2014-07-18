/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.Mapping;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UserAdapter extends ArrayAdapter<Mapping>{

	public UserAdapter(Context context, int resource, List<Mapping> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.user_profile_mapme_grid, null);
		TextView nickname = (TextView)convertView.findViewById(R.id.textViewNickname);
		Mapping c = getItem(position);
		nickname.setText(c.getUser().getNickname());
		TextView email = (TextView)convertView.findViewById(R.id.textViewEmail);
		email.setText(c.getUser().getEmail());
		return convertView;
	}

}