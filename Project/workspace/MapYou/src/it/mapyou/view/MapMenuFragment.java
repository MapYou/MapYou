/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.util.UtilAndroid;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.audiofx.BassBoost.Settings;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMenuFragment extends Fragment{
	
	public static final String TAG = "FACEBOOK";
	private Facebook mFacebook;
	public static final String APP_ID = "1516574998563667";

	private AsyncFacebookRunner mAsyncRunner;
	private static final String[] PERMS = new String[] { "read_stream" };
	public SharedPreferences sharedPrefs;
	private Context mContext;


	static final String EXTRA_MAP = "map";

	static final LauncherIcon[] ICONS = {
		new LauncherIcon(R.drawable.mappp, "New MapMe", NewMapMe.class),
		new LauncherIcon(R.drawable.comm2, "My MapMe", YourMapMeActivity.class),
		new LauncherIcon(R.drawable.invites, "Request partecipation", YourMapMeActivity.class),
		new LauncherIcon(R.drawable.menu_notification, "Notification", NotificationTabHome.class),
		new LauncherIcon(R.drawable.settings, "Settings", SettingsActivity.class),
		new LauncherIcon(R.drawable.logoooouttt, "Logout", null)
	};

	static class LauncherIcon {
		String text;
		int imgId;
		Class<?> clazz;

		public LauncherIcon(int imgId, String text, Class<?> c) {
			this.imgId = imgId;
			this.text = text;
			this.clazz = c;
		}

		/**
		 * @return the clazz
		 */
		public Class<?> getClazz() {
			return clazz;
		}

		/**
		 * @return the imgId
		 */
		public int getImgId() {
			return imgId;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}

	class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public LauncherIcon getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		class ViewHolder {
			public ImageView icon;
			public TextView text;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

				v = vi.inflate(R.layout.dashboard_icon, null);
				holder = new ViewHolder();
				holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
				holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			holder.icon.setImageResource(ICONS[position].imgId);
			holder.text.setText(ICONS[position].text);

			return v;
		}
	}
	
	public void setConnection() {
		mContext = getActivity();
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.dashboard, container, false);

		GridView gridview = (GridView) view.findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(getActivity()));

		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long idd) {

				if(position==ICONS.length-1){
					logout();
				}else{
					Class<?> c = ICONS[position].getClazz();
					if(c!=null){
						Intent it = new Intent(getActivity(), c);
						if(c == YourMapMeActivity.class){
							it.putExtra("inclusion", 
									ICONS[position].getText().equals("My MapMe"));
						}else;
						getActivity().startActivity(it);
					}
				}
				 
			}
		});
		return view;
	}
	
	private void logout(){
		sharedPrefs=PreferenceManager.getDefaultSharedPreferences(getActivity());

		final String id=sharedPrefs.getString("idface","");
		final String email=sharedPrefs.getString("emailFace","");
		final String name=sharedPrefs.getString("nameFace","");

		String u=sharedPrefs.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "") ;
		String emailUser=sharedPrefs.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, "");
		if(!u.equalsIgnoreCase("")){
			sharedPrefs.edit().clear();
			Intent i = new Intent(getActivity(), Login.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}else{
			new AsyncTask<Void, Void, Boolean>(){

				@Override
				protected Boolean doInBackground(Void... params){
					setConnection();
					if (Session.getActiveSession() != null)
						Session.getActiveSession().closeAndClearTokenInformation();

					Editor  editor = sharedPrefs.edit();
					editor.clear();
					editor.commit(); 

					Log.v("accesscix", sharedPrefs.getString("access_token", "x"));
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result){
					
					if (result == null|| result == false){
						UtilAndroid.makeToast(mContext, "Not_logout", 5000);
						return;
					}else{
						Intent i = new Intent(getActivity(), Login.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
				}
			}.execute();
		}
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {  
		super.onActivityCreated(savedInstanceState);  


	}
}

