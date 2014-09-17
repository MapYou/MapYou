/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.network.AbstractAsyncTask;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
public class NotificationAdapter extends BaseAdapter{

	private Context cont;
	private List<Notification> notif;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public NotificationAdapter(Context act, List<Notification> notif) {
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
		Notification m = notif.get(position);
//		dat.setText(sdf.format(m.getDate().getTime()));
		if(m.getNotificationType().equals("SEND")){
			title.setText("MapYou: invite for mapme");
			message.setText("You have received an invitation from \""
					+m.getNotifier().getNickname()+"\"");
		}else {
			title.setText("MapYou: request to partecipate");
			message.setText("You have received a request to partecipate by \""+
					m.getNotifier().getNickname()+"\"");
		}
//		else if(m.getNotificationType().equals("CHAT")){
//			title.setText("MapYou: chat message");
//			message.setText("You have received a message from \""+
//					m.getNotifier().getNickname()+"\"");
//		}

		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Notification m = notif.get(position);
				if(m.getNotificationType().equals("SEND") || 
				m.getNotificationType().equals("REQUEST")){
					notificationSend_Request(m);
				}
			}
		});
		return convertView;
	}
	
//	public void viewChatNotification(final Notification no){
//		AlertDialog	alert2= new AlertDialog.Builder(cont).create();
//		alert2.setTitle("Chat message");
//		alert2.setIcon(R.drawable.profile);
//		alert2.setButton("Ok", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		alert2.show();
//	}

	public void notificationSend_Request(final Notification no){
		AlertDialog	alert2= new AlertDialog.Builder(cont).create();
		alert2.setTitle(no.getNotificationType().equals("REQUEST")?
				"Request to partecipate"
				:
					"You are invited");
		alert2.setMessage(no.getNotificationType().equals("REQUEST")?
				"Do you want add \""+no.getNotifier().getNickname()
				+ "\" into your mapme \""+no.getNotificationObject().getName()+"\" ?"
				:
					"\""+no.getNotifier().getNickname()+ "\" has invited you to partecipate in mapme"
							+ " \""+no.getNotificationObject().getName()+"\"");
		alert2.setIcon(R.drawable.ic_launcher);
		alert2.setButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert2.setButton2("Accept", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new AcceptPartecipation(no, cont).execute(true);
			}
		});
		alert2.setButton3("Ignore", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new AcceptPartecipation(no, cont).execute(false);
			}
		});

		alert2.show();
	}
	
	class AcceptPartecipation extends AbstractAsyncTask<Boolean, Void, String>{

		private Notification n;
		
		public AcceptPartecipation(Notification n, Context cont) {
			super(cont);
			this.n = n;
		}
		
		@Override
		protected String doInBackground(Boolean... params) {

			try {
				String resp=null;
				parameters.put("iduser", URLEncoder.encode(""+(n.getNotificationType().equals("REQUEST")?n.getNotifier().getModelID():n.getNotified().getModelID()), "UTF-8"));
				parameters.put("idnot",""+n.getModelID());
				parameters.put("idm", ""+n.getNotificationObject().getModelID());
				parameters.put("isAccept", ""+String.valueOf(params[0]));
				resp=DeviceController.getInstance().getServer().request(SettingsServer.MANAGEMENT_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));


				return resp;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void newOnPostExecute(String result) {

			if(result!=null){
				if(n.getNotificationType().equals("SEND")){
					if(result.contains("true")){
						UtilAndroid.makeToast(cont.getApplicationContext(), "You are added in "+n.getNotificationObject().getName(),5000);				
						notif.remove(n);
						notifyDataSetChanged();
					}else if(result.contains("false")){
						UtilAndroid.makeToast(cont.getApplicationContext(), "Error: you are already added in "+n.getNotificationObject().getName(),5000);				
						notif.remove(n);
						notifyDataSetChanged();
					}else if(result.contains("refused")){
						UtilAndroid.makeToast(cont.getApplicationContext(), "You are refused invite in "+n.getNotificationObject().getName(),5000);				
						notif.remove(n);
						notifyDataSetChanged();
					}else if(result.contains("error")){
						UtilAndroid.makeToast(cont.getApplicationContext(), "Error",5000);				
					}else{
						UtilAndroid.makeToast(cont.getApplicationContext(), "Server error.",5000);				
					}
				}else {

				}

			}else{
				UtilAndroid.makeToast(cont.getApplicationContext(), "Connection error.",5000);				

			}


		}
	}
	
}
