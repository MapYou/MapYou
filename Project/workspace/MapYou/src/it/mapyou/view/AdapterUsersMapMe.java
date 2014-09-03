package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterUsersMapMe extends BaseAdapter {

	private List<User> map;
	private Context cont;
	private MapMe mapme;

	public AdapterUsersMapMe(Context cont, List<User> map, MapMe mapme) {
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
			convertView=View.inflate(cont, R.layout.user_profile_mapme_grid, null);
		}

		TextView n= (TextView) convertView.findViewById(R.id.textViewNickname);
		ImageView icon=(ImageView) convertView.findViewById(R.id.imageView1);

		User user = map.get(position);

		if(user.getNickname().equals(mapme.getAdministrator().getNickname())){
			icon.setImageResource(R.drawable.admin);
			n.setText("Admin:\n"+user.getNickname());
		}else
			n.setText(user.getNickname());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new RetrieveMapping().execute(map.get(position).getModelID());
			}
		});
		return convertView;
	} 

	class RetrieveMapping extends AsyncTask<Integer, Void, JSONObject>{


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
		protected JSONObject doInBackground(Integer... params) {

			try {
				parameters.put("user", String.valueOf(params[0]));
				parameters.put("mapme", String.valueOf(mapme.getModelID()));
				JSONObject response=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_ALL_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			p.dismiss();
			if(result==null){
				UtilAndroid.makeToast(cont, "Please refresh....", 5000);
			}else{
				MappingUser mp= getMappingFromMapme(result);
				if(mp!=null){
					if(mp.getModelID()>0){
						Util.CURRENT_MAPPING = mp;
						Bundle b = new Bundle();
						b.putParcelable("mapping", mp);
						Intent i = new Intent(cont.getApplicationContext(), UserOnMapMe.class);
						i.putExtras(b);
						cont.startActivity(i);
					}else
						UtilAndroid.makeToast(cont, "This user has not registered yours position.", 5000);
				}
				else
					UtilAndroid.makeToast(cont, "Error while fetching your position on mapme.", 5000);
			}
		}

	}

	public MappingUser getMappingFromMapme (JSONObject json){


		try {
			JSONArray jsonArr= json.getJSONArray("Mapping");

			MappingUser m= new MappingUser();
			json=jsonArr.getJSONObject(0);
			User admin = getUserByJSon(json.getJSONArray("user"));
			Point point = getPointByJSon(json.getJSONArray("point"));
			if(admin!=null && point!=null){
				m.setModelID(Integer.parseInt(json.getString("id")));
				m.setUser(admin);
				m.setPoint(point);
			}else;
			return m;
		}catch (Exception e) {
			return null;
		}
	}

	private User getUserByJSon (JSONArray jsonArr){

		try {
			User user= new User();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private Point getPointByJSon (JSONArray jsonArr){

		try {
			Point ptn= new Point();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
				ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
				ptn.setLocation(json.getString("location"));
			}
			return ptn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
}

