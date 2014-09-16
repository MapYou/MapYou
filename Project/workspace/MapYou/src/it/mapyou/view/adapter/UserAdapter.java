/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.model.MappingUser;

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
public class UserAdapter extends ArrayAdapter<MappingUser>{

	public UserAdapter(Context context, int resource, List<MappingUser> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.user_profile_mapme_grid, null);
		TextView nickname = (TextView)convertView.findViewById(R.id.textViewNickname);
		MappingUser c = getItem(position);
		nickname.setText(c.getUser().getNickname());
		TextView email = (TextView)convertView.findViewById(R.id.textViewEmail);
		email.setText(c.getUser().getEmail());
		return convertView;
	}

}
