/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ProfileFacebookFragment extends FacebookControllerFragment  {

	private SharedPreferences sp;
	private ImageView img;
	private TextView nameUser;
	private TextView mailUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.profile_facebook, container, false);

		TextView logout = (TextView) view.findViewById(R.id.textView3);
		img=(ImageView)view.findViewById(R.id.imageFace);
		nameUser= (TextView)view.findViewById(R.id.textprofileUser);
		mailUser= (TextView) view.findViewById(R.id.textprofileemail);
		sp=PreferenceManager.getDefaultSharedPreferences(getActivity());

		final String id=sp.getString("idface","");
		final String email=sp.getString("emailFace","");
		final String name=sp.getString("nameFace","");

		String u=sp.getString("nickname", "") ;
		if(!u.equalsIgnoreCase("")){
			nameUser.setText(u);
			img.setImageResource(R.drawable.usersimple);
			logout.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					logoutSimple();
					sp.edit().clear();
					
				}
			}); 
		}else{
			setData(email, name);
			getImageProfile(id,img);
			logout.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					logoutFacebook();
				}
			}); 
		}
		return view;
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {  
		super.onActivityCreated(savedInstanceState);  
	}

	public void setData(String e, String n){
		nameUser.setText(n);
		mailUser.setText(e);
	}
	
	 
	@SuppressWarnings("deprecation")
	public void logoutFacebook (){

		AlertDialog	alert2= new AlertDialog.Builder(getActivity()).create();
		alert2.setTitle("MapYou");
		alert2.setMessage("Log out?");
		alert2.setIcon(R.drawable.ic_launcher);
		alert2.setButton2("No", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert2.setButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						logoutFacebookSession();
					}
				}).start();

			}
		});

		alert2.show();
	}


}
