package it.mapyou.view.adapter;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterUsersMapMeOnDeleteUser extends BaseAdapter {

	private List<User> map;
	private Context cont;
	private MapMe mapme;
	private boolean[] onClicked;
	private int defaultColor, clickedColor;

	public AdapterUsersMapMeOnDeleteUser(Context cont, List<User> map, MapMe mapme) {
		this.cont = cont;
		this.map = map;
		this.mapme = mapme;
		onClicked  =new boolean[map.size()];
		defaultColor = Color.TRANSPARENT;
		clickedColor = Color.LTGRAY;
	}

	@Override
	public int getCount() {
		return map.size();
	}

	@Override
	public Object getItem(int position) {
		return map.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	public void addItem(User i){
		map.add(i);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView=View.inflate(cont, R.layout.user_profile_mapme_grid, null);
		}
		
		if(onClicked[position])
			convertView.setBackgroundColor(clickedColor);
		else
			convertView.setBackgroundColor(defaultColor);
		
		
		TextView n= (TextView) convertView.findViewById(R.id.textViewNickname);
		ImageView icon=(ImageView) convertView.findViewById(R.id.imageView1);

		User user = map.get(position);
		n.setText(user.getNickname());

		final View vv = convertView;
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClicked[position]=onClicked[position]?false:true;
				if(onClicked[position])
					vv.setBackgroundColor(clickedColor);
				else
					vv.setBackgroundColor(defaultColor);
			}
		});
		return convertView;
	}
	
	public void deleteUser(){
		if(countDeleting()>0)
			new DeleteUser().execute();
		else
			UtilAndroid.makeToast(cont, "There are no users to deleting.", 5000);
	}

	class DeleteUser extends AsyncTask<Void, Void, String>{


		private HashMap<String, String> parameters=new HashMap<String, String>();
		private ProgressDialog p;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(cont))
				UtilAndroid.makeToast(cont, "Internet Connection not found", 5000);
			else{
				p = new ProgressDialog(cont);
				p.setMessage("Loading...");
				p.setIndeterminate(false);
				p.setCancelable(false);
				p.show();
			}

		}


		@Override
		protected String doInBackground(Void... params) {

			try {
				parameters.put("users", tokenize());
				parameters.put("idm", String.valueOf(mapme.getModelID()));
				parameters.put("admin", String.valueOf(mapme.getAdministrator().getModelID()));
				String response=DeviceController.getInstance().getServer().
						request(SettingsServer.DELETE_USER_IN_MAPME, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			p.dismiss();
			if(result==null){
				UtilAndroid.makeToast(cont, "Please refresh....", 5000);
			}else{
				try {
					if(Integer.parseInt(result)==countDeleting()){
						for(int i=onClicked.length-1; i>=0; i--)
							if(onClicked[i])
								{
								map.remove(i);
								}
						
						onClicked = new boolean[map.size()];
						
						notifyDataSetChanged();
					}else
						UtilAndroid.makeToast(cont, "Erro while deleting user.", 5000);
				} catch (Exception e) {
					UtilAndroid.makeToast(cont, "Server error.", 5000);
				}
			}
		}

	}
	
	private int countDeleting(){
		int c=0;
		for(int i=0; i<onClicked.length; i++){
			if(onClicked[i])
				c++;
		}
		return c;
	}
	
	private String tokenize(){
		StringBuilder b = new StringBuilder();
		for(int i=0; i<onClicked.length; i++){
			if(onClicked[i]){
				b.append(map.get(i).getNickname()+";");
			}
		}
		
		return b.toString();
	}
	
}

