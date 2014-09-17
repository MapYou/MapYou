/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.ChatMessage;
import it.mapyou.network.AbstractAsyncTask;
import it.mapyou.network.SettingsServer;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatNotificatioAdapter extends BaseAdapter{

	private Context cont;
	private List<ChatMessage> notif;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public ChatNotificatioAdapter(Context act, List<ChatMessage> notif) {
		this.cont = act;
		this.notif = notif;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notif.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notif.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.notification_list_row, null);
		}

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView message = (TextView) convertView.findViewById(R.id.message);
		TextView dat = (TextView) convertView.findViewById(R.id.textViewDate);
		ChatMessage m = notif.get(position);
		GregorianCalendar g = m.getDate();
		if(g!=null)
			dat.setText(sdf.format(g.getTime()));
		else
			dat.setText("");
		title.setText("\""+m.getNotificationObject().getName()+"\": chat message" );
		message.setText("You have received a message from \""+
				m.getNotifier().getNickname()+"\"");

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChatMessage m = notif.get(position);
				new UpdateNotification(cont).execute(m);
			}
		});
		return convertView;
	}

	public void viewChatNotification(final ChatMessage no){
		AlertDialog	alert2= new AlertDialog.Builder(cont).create();
		alert2.setTitle("Mapme \""+no.getNotificationObject().getName()+"\" --> Chat message");
		alert2.setMessage(no.getMessage());
		alert2.setIcon(R.drawable.profile);
		alert2.setButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				notif.remove(no);
				notifyDataSetChanged();
				dialog.cancel();
			}
		});

		alert2.show();
	}
	
	class UpdateNotification extends AbstractAsyncTask<ChatMessage, Void, ChatMessage>{

		/**
		 * @param cont
		 */
		public UpdateNotification(Context cont) {
			super(cont);
			// TODO Auto-generated constructor stub
		}
		@Override
		protected ChatMessage doInBackground(ChatMessage... params) {

			try {
				parameters.put("idNot", String.valueOf(params[0].getModelID()));
				parameters.put("state", "READ");

				DeviceController.getInstance().getServer().
				request(SettingsServer.UPDATE_NOT, DeviceController.getInstance().getServer().setParameters(parameters));

				return params[0];

			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void newOnPostExecute(ChatMessage result) {
			
			if(result != null){
				viewChatNotification(result);
			}
		}
	}


}