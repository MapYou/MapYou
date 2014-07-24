

package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Route;
import it.mapyou.model.StartPoint;
import it.mapyou.model.User;
import it.mapyou.navigator.ConfigurationGeocodingApi;
import it.mapyou.navigator.ParserDataFromGeocoding;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class TabFragment extends Fragment {

	private static final int DIALOG = 1;
	private static final int DIALOG2 = 2;
	private GoogleMap googleMap;
	private EditText start;
	private EditText dest;
	private EditText nameMapMe;

	private Marker startMarker;
	private Marker endMarker;
	private SharedPreferences sp;
	private Activity act;
	private DeviceController controller;
	private CharSequence[] elements=null;
	private CharSequence[] elements2=null;
	String address="";
	String lat="";
	String lon="";
	boolean isStart=false;
	private static MapMe mapmeNew;
	private Builder builder;
	private Builder builder2;
	private int count=0;

	/**
	 * @return the mapmeNew
	 */
	public static MapMe getMapmeNew() {
		return mapmeNew;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.new_map_me2, container, false);
		start= (EditText) view.findViewById(R.id.EditTextStartMapme);
		dest= (EditText) view.findViewById(R.id.editTextDestinazione);
		nameMapMe= (EditText) view.findViewById(R.id.EditTextNameMapMe2);
		act=getActivity();
		sp=PreferenceManager.getDefaultSharedPreferences(getActivity());
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		controller= new DeviceController();
		try {
			controller.init(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		initilizeMap();
		
		Button s = (Button)view.findViewById(R.id.ButtonStartMapme2);
		s.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String add= start.getText().toString().replace(" ", "%20");
				if(startMarker!=null)
					UtilAndroid.makeToast(getActivity(), "Start location can be setted once", 5000);
				else{
					if(add!=null && add.length()>0){
						String url= ConfigurationGeocodingApi.getUrlFrom(add);
						new SettingsTask(true).execute(url);
					}
					else
						UtilAndroid.makeToast(getActivity(), "Field cannot be empty", 5000);
				}
			}
		});
		
		Button d = (Button)view.findViewById(R.id.buttonStartDestinat);
		d.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String add= dest.getText().toString().replace(" ", "%20");
				if(endMarker!=null)
					UtilAndroid.makeToast(getActivity(), "Destionation location can be setted once", 5000);
				else{
					if(add!=null && add.length()>0){
						String url= ConfigurationGeocodingApi.getUrlFrom(add);
						new SettingsTask(false).execute(url);
					}
					else
						UtilAndroid.makeToast(getActivity(), "Field cannot be empty", 5000);
				}
			}
		});
		
		((Button)view.findViewById(R.id.Button01)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String nickname= sp.getString("nickname", "");
				String nameMapMee= nameMapMe.getText().toString();
				if(nickname.length()>0 && nameMapMee!=null && nameMapMee.length()>0 && startMarker!=null && endMarker!=null){
					double slat = startMarker.getPosition().latitude;
					double slong = startMarker.getPosition().longitude;
					String sadd = startMarker.getSnippet();

					double elat = endMarker.getPosition().latitude;
					double elong = endMarker.getPosition().longitude;
					String eadd = endMarker.getSnippet();

					MapMe mapMe = new MapMe();
					Route route = new Route();
					User admin = new User();
					admin.setNickname(nickname);
					StartPoint startPoint= new StartPoint();
					EndPoint endPoint = new EndPoint();
					startPoint.setLatitude(slat);
					startPoint.setLongitude(slong);
					endPoint.setLatitude(elat);
					endPoint.setLongitude(elong);
					route.setStartPoint(startPoint);
					route.setEndPoint(endPoint);
					mapMe.setRoute(route);
					mapMe.setName(nameMapMee);
					mapMe.setAdministrator(admin);
					mapMe.setStartAddress(sadd);
					mapMe.setEndAddress(eadd);

					SaveMapMe sv = new SaveMapMe();
					sv.execute(mapMe);

				}else
					UtilAndroid.makeToast(getActivity(), "Please insert all required information.", 5000);

			}
		});
		
		((Button)view.findViewById(R.id.buttonSave)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(googleMap!=null && (startMarker!=null || endMarker!=null)){
					googleMap.clear();startMarker=null;endMarker=null;
					Intent i= new Intent(act, NewMapMe.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}else
					UtilAndroid.makeToast(getActivity(), "Insert a location before cleaning map.", 5000);
			}
		});
		
		return view;
	}

	class SaveMapMe extends AsyncTask<MapMe, Void, MapMe>{

		String response="";
		HashMap<String, String> parameters= new HashMap<String, String>();

		@Override
		protected MapMe doInBackground(MapMe... params) {


			try {
				Route r= params[0].getRoute();
				parameters.put("user", URLEncoder.encode(params[0].getAdministrator().getNickname().toString(), "UTF-8"));

				parameters.put("name", URLEncoder.encode(params[0].getName().toString(), "UTF-8"));
				parameters.put("slat", ""+r.getStartPoint().getLatitude());
				parameters.put("slon", ""+r.getStartPoint().getLongitude());
				parameters.put("elat", ""+r.getEndPoint().getLatitude());
				parameters.put("elon", ""+r.getEndPoint().getLongitude());
				parameters.put("sadd", URLEncoder.encode(params[0].getStartAddress().toString(), "UTF-8"));
				parameters.put("eadd", URLEncoder.encode(params[0].getEndAddress().toString(), "UTF-8"));
				response=controller.getServer().request(SettingsServer.NEW_MAPME, controller.getServer().setParameters(parameters));

				return params[0];
			} catch (UnsupportedEncodingException e) {
				return null;
			}

		}

		@Override
		protected void onPostExecute(MapMe result) {
			super.onPostExecute(result);

			if(result!=null && response.contains("added") && response!=""){

				mapmeNew = result;
				Bundle b = new Bundle();
				b.putParcelable("mapme", mapmeNew);
				Intent i = new Intent(act, MapMeLayoutHome.class);
				i.putExtras(b);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}
			else
				UtilAndroid.makeToast(act, "MapMe doesn't create", 3000);
		}

	}

	class SettingsTask extends AsyncTask<String, Void, List<HashMap<String,String>>>{

		SettingsTask(boolean isStartt) {
			isStart=isStartt;

		}

		@Override
		protected List<HashMap<String, String>> doInBackground(String... params) {

			try {
				List<HashMap<String, String>> listOfpoints= null;
				ParserDataFromGeocoding parser= new ParserDataFromGeocoding();
				String data= parser.retrieveData(params[0]);
				listOfpoints= parser.parse(new JSONObject(data));

				return listOfpoints;
			} catch (JSONException e) {

				return null;
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			super.onPostExecute(result);

			List<HashMap<String, String>> allElements= new ArrayList<HashMap<String,String>>();
			if(result!=null && result.size()>0){

				if(result.size()==1){
					for(HashMap<String, String> m: result){
						lat=m.get("lat");
						lon=m.get("lng");
						address=m.get("formatted_address");
					}

				}else;
				if(result.size()>0 && result.size()!=1){
					address=null;
					for(int i=0; i<result.size(); i++){
						HashMap<String, String> ele= result.get(i);
						lat=ele.get("lat");
						lon=ele.get("lng");
						address=ele.get("formatted_address");
						allElements.add(ele);
					}
					address=null;
					elements=null;
					elements=getAllElements(allElements);
					
				}else;
				if(address==null && count==0){
					count=1;
					customDialogSelected().show();
				}
				else if(address==null && count>0){
					elements2=getAllElements(allElements);
					count=0;
					customDialogSelected2().show();
					
				}
				else{
					double latitude= Double.parseDouble(""+lat);
					double longitude= Double.parseDouble(""+lon);

					MarkerOptions opt= new MarkerOptions();
					opt.title(isStart?"Partenza":"Arrivo");
					opt.snippet(address);
					opt.position(new LatLng(latitude, longitude));

					if(isStart)
					{
						opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
						startMarker = googleMap.addMarker(opt);
					}
					else
					{
						opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
						endMarker = googleMap.addMarker(opt);
					}

					CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
					googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
				}
			}else
				UtilAndroid.makeToast(getActivity(), "Address not valid.", 5000);

		}
	}

	public void setAddress(String add, String lat, String lon){

		StringTokenizer address= new StringTokenizer(add,"_");
		double latitude= Double.parseDouble(""+lat);
		double longitude= Double.parseDouble(""+lon);

		MarkerOptions opt= new MarkerOptions();
		opt.title(isStart?"Partenza":"Arrivo");
		opt.snippet(address.nextToken());
		opt.position(new LatLng(latitude, longitude));

		if(isStart){
			opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			startMarker = googleMap.addMarker(opt);
		}
		else{
			opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			endMarker = googleMap.addMarker(opt);
		}
		CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));


	}

	public CharSequence[] getAllElements (List<HashMap<String, String>> maps){

		HashMap<String, String> map=null;
		List<String> listItems = new ArrayList<String>();
		CharSequence[] charSequenceItems =null;

		for(int i=0; i<maps.size(); i++){
			map=maps.get(i);
			listItems.add(map.get("formatted_address")+"_"+map.get("lat")+"_"+map.get("lng"));
		}
		charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);
		return charSequenceItems;

	}

	public CharSequence[] viewElements(CharSequence[] elements){
		CharSequence[] items=null;
		List<String> listItems = new ArrayList<String>();
		for(int i=0; i<elements.length; i++){
			CharSequence ele= elements[i];
			StringTokenizer t= new StringTokenizer(ele.toString(), "_");
			listItems.add(t.nextToken());

		}
		items=listItems.toArray(new CharSequence[listItems.size()]);
		return items;
	}


	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getActivity(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public Dialog customDialogSelected(){

		builder = new AlertDialog.Builder(act);
		builder.setTitle("Select your address");
		builder.setCancelable(false);
		builder.setSingleChoiceItems(viewElements(elements), -1, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				if(elements[whichButton] != null){
					lat=null;
					lon=null;
					address=null;
					address=elements[whichButton].toString();
					StringTokenizer t= new StringTokenizer(address, "_");
					t.nextToken();
					while(t.hasMoreTokens()){
						lat=t.nextToken().toString();
						lon=t.nextToken().toString();
					}
				}
			}
		});
		builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				setAddress(address, lat,lon);
				dialog.dismiss();
				dialog.dismiss();
				dialog.cancel();
				elements=null;
				builder=null;
			}
		});
		return builder.create();

	}

	public Dialog customDialogSelected2(){

		builder2 = new AlertDialog.Builder(act);
		builder2.setTitle("Select your address");
		builder2.setCancelable(false);
		builder2.setSingleChoiceItems(viewElements(elements2), -1, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				if(elements2[whichButton] != null){
					lat=null;
					lon=null;
					address=null;
					address=elements2[whichButton].toString();
					StringTokenizer t= new StringTokenizer(address, "_");
					t.nextToken();
					while(t.hasMoreTokens()){
						lat=t.nextToken().toString();
						lon=t.nextToken().toString();
					}
				}
			}
		});
		builder2.setPositiveButton("Select", new DialogInterface.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				setAddress(address, lat,lon);
				dialog.dismiss();
				dialog.cancel();
				elements2=null;
				builder2=null;
			}
		});
		return builder2.create();

	}
	
}




