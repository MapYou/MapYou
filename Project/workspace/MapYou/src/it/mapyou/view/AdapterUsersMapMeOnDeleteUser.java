package it.mapyou.view;

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

	public AdapterUsersMapMeOnDeleteUser(Context cont, List<User> map, MapMe mapme) {
		this.cont = cont;
		this.map = map;
		this.mapme = mapme;
		
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
			convertView=View.inflate(cont, R.layout.user_profile_grid_delete_in_mapme, null);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNicknameDeleteUser);
		ImageView icon=(ImageView) convertView.findViewById(R.id.imageViewDeleteUser);

		User user = map.get(position);
		n.setText(user.getNickname());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DeleteUser(map.get(position).getModelID()).execute();
			}
		});
		return convertView;
	} 

	class DeleteUser extends AsyncTask<Void, Void, String>{


		private HashMap<String, String> parameters=new HashMap<String, String>();
		private ProgressDialog p;
		private int userId;
		
		public DeleteUser(int userId) {
			this.userId = userId;
		}

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
				parameters.put("idu", String.valueOf(userId));
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
				if(result.contains("1")){
					for(int i=0; i<map.size(); i++)
						if(map.get(i).getModelID()==userId)
							{
							map.remove(i);
							break;
							}
					
					notifyDataSetChanged();
				}else
					UtilAndroid.makeToast(cont, "Erro while deleting user.", 5000);
			}
		}

	}
	
}

