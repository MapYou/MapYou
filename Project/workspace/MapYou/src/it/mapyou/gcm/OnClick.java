package it.mapyou.gcm;

import it.mapyou.model.User;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
 
 

public class OnClick extends Activity implements OnItemClickListener  {

	private List<User> l;
	private Activity actt;
	private SharedPreferences sp;

	public OnClick(Activity a, List<User> lis) {

		this.actt=a;
		this.l=lis;
		sp=PreferenceManager.getDefaultSharedPreferences(actt.getApplicationContext());
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		User u = l.get(position);
		Editor ed= sp.edit();
//		ed.putString("uu", u.getName());
//		ed.putString("idreg", u.getRegId());
//		 
		ed.commit();
		Intent i= new Intent(actt, Mess.class);
		actt.startActivity(i);

	}

}
