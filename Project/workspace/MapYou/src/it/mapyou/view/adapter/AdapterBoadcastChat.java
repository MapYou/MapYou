package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.model.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class AdapterBoadcastChat  extends BaseAdapter{

	private List<ChatMessage> not;
	private int currentUserId;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public AdapterBoadcastChat(List<ChatMessage> n, int currentUserId) {
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
			convertView=View.inflate(parent.getContext(), R.layout.broadcat_single_chat, null);
		}

		TextView t = (TextView)convertView.findViewById(R.id.textMessage);
		TextView userSend = (TextView)convertView.findViewById(R.id.textView1);
		ChatMessage n = not.get(position);
		t.setText(n.getMessage());
		userSend.setText(n.getNotifier().getNickname());
		TextView dat = (TextView) convertView.findViewById(R.id.textMessageDateB);
		GregorianCalendar g = n.getDate();
		if(g!=null)
			dat.setText(getDay(g.get(Calendar.DAY_OF_WEEK))+"  "+sdf.format(g.getTime()));
		else;
		if(n.getNotifier().getModelID()==currentUserId){
			t.setBackgroundResource(R.drawable.bubble_a);
			t.setGravity(Gravity.RIGHT);
		}else{
			t.setBackgroundResource(R.drawable.bubble_b);
			t.setGravity(Gravity.LEFT);

		}


		return convertView;

	}

	private String getDay(int i) {
		switch (i) {
		case 2:
			return "Luned�";
		case 3:
			return "Marted�";
		case 4:
			return "Mercoled�";
		case 5:
			return "Gioved�";
		case 6:
			return "Venerd�";
		case 7:
			return "Sabato";
		case 1:
			return "Domenica";
		default:
			return "";
		}
	}
}

