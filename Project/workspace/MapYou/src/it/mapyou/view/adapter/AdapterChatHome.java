/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.model.User;
import it.mapyou.view.ChatUserToUser;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
			convertView=View.inflate(cont, R.layout.chat_adapter, null);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNicknameChatAdapter);
//		ImageView icon=(ImageView) convertView.findViewById(R.id.imageViewChatAdapter);

		User user = u.get(position);
		n.setText(user.getNickname());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				User user = u.get(position);
				Intent it = new Intent(cont, ChatUserToUser.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				Bundle b = new Bundle();
				b.putSerializable("user", user);
				it.putExtras(b);
				cont.startActivity(it);
			}
		});
		return convertView;
	} 

}
