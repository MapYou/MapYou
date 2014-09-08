/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.User;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class AdapterChatHome extends BaseAdapter {

	private List<User> u;
	private Context cont;
 

	public AdapterChatHome(Context cont, List<User> users ) {
		this.cont = cont;
		this.u = users;
		
	}

	@Override
	public int getCount() {
		return u.size();
	}

	@Override
	public Object getItem(int position) {
		return u.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	public void addItem(User i){
		u.add(i);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView=View.inflate(cont, R.layout.user_profile_mapme_grid, null);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNickname);
		ImageView icon=(ImageView) convertView.findViewById(R.id.imageView1);

		User user = u.get(position);

//		if(user.getNickname().equals(mapme.getAdministrator().getNickname())){
//			icon.setImageResource(R.drawable.admin);
//			n.setText("Admin:\n"+user.getNickname());
//		}else
//			n.setText(user.getNickname());
//
//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				new RetrieveMapping().execute(map.get(position).getModelID());
//			}
//		});
		return convertView;
	} 

}
