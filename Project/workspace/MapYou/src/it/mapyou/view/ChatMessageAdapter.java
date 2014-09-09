/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.Notification;

import java.util.List;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatMessageAdapter extends BaseAdapter{

	private List<Notification> not;
	private int currentUserId;
	
	public ChatMessageAdapter(List<Notification> n, int currentUserId) {
		this.not = n;
		this.currentUserId = currentUserId;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return not.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return not.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.chat_single_message_adapter, null);
		}
		
		TextView t = (TextView)convertView.findViewById(R.id.textMessage);
		Notification n = not.get(position);
		t.setText(n.getNotificationState());
		if(n.getNotifier().getModelID()==currentUserId)
			{
			t.setBackgroundColor(Color.GRAY);
			t.setGravity(Gravity.RIGHT);
			}
		else
			{
			t.setBackgroundColor(Color.YELLOW);
			t.setGravity(Gravity.LEFT);
			}
		
		return convertView;
	}

}
