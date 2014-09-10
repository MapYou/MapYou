/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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

	private Activity cont;
	private List<Notification> notif;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public ChatNotificatioAdapter(Activity cont, List<Notification> notif) {
		this.cont = cont;
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
		Notification m = notif.get(position);
		GregorianCalendar g = m.getDate();
		if(g!=null)
			dat.setText(sdf.format(g.getTime()));
		else
			dat.setText("");
		title.setText("MapYou: chat message in mapme \""+m.getNotificationObject().getName()+"\"");
		message.setText("You have received a message from \""+
				m.getNotifier().getNickname()+"\"");

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Notification m = notif.get(position);
				new UpdateNotification().execute(m);
			}
		});
		return convertView;
	}

	public void viewChatNotification(final Notification no){
		AlertDialog	alert2= new AlertDialog.Builder(cont).create();
		alert2.setTitle("Mapme \""+no.getNotificationObject().getName()+"\" --> Chat message");
		alert2.setMessage(no.getNotificationState());
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
	
	class UpdateNotification extends AsyncTask<Notification, Void, Notification>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(cont.getApplicationContext()))
				UtilAndroid.makeToast(cont.getApplicationContext(), "Internet Connection not found", 5000);

		}

		// CHAT CHAT_BROADCAST
		@Override
		protected Notification doInBackground(Notification... params) {

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
		protected void onPostExecute(Notification result) {
			super.onPostExecute(result);
			if(result != null){
				viewChatNotification(result);
			}
		}
	}


}