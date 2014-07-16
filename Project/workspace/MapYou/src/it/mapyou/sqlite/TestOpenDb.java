/**
 * 
 */
package it.mapyou.sqlite;

import it.mapyou.model.User;
import it.mapyou.persistence.DAOCreator;
import it.mapyou.persistence.impl.SQLiteDAOManager;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
 

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class TestOpenDb extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_map_you_main);
	
 
		 
		DAOCreator factory= DAOCreator.getInstance();
		SQLiteDAOManager s=SQLiteDAOManager.getInstance(getApplicationContext());
		s.connect();
		 
		 
//		int a=s.getDb().getVersion();
//		//Toast.makeText(getApplicationContext(), String.valueOf(a), 100000).show();
//		Log.v("PATH DB",String.valueOf(a));
//		s.delete();
	//	s.close(); //close
//		
	 
		 
//		User u= new User();
//		u.setModelID(9);
//		u.setEmail("@");
//		u.setFirstname("BASILIO");
//		u.setLastname("Fusco2");
//		u.setPassword("1234");
//		u.setNickname("peppeCixiiiiiii");
//		s.getUserDAO().insert(u);
//		
		StringBuffer ss= new StringBuffer();
		List<User> prova= s.getUserDAO().selectAll();
		for(User uu: prova)
			ss.append(uu.getModelID()+"\n"+uu.getEmail()+"\n"+uu.getFirstname()+"\n"+uu.getLastname()+"\n"+uu.getPassword()+"\n"+uu.getNickname()+"\n");
	
		Log.v("PATH DB",ss.toString());
		Toast.makeText(getApplicationContext(),ss, 100000).show();
		 
		
//		db_map_you.insertUser(u);
////		
////		//retrieve
//		
//		ArrayList<User> users= db_map_you.getAllUser();
//		StringBuffer s= new StringBuffer();
//		for(User uu: users)
//			s.append(uu.getModelID()+"\n"+uu.getEmail()+"\n"+uu.getFirstname()+"\n"+uu.getLastname()+"\n"+uu.getPassword()+"\n"+uu.getNickname()+"\n");
//		
		//Log.v("PATH DB",db_map_you.getDb().getPath());
		//Toast.makeText(getApplicationContext(), db_map_you.getDb().getPath(), 100000).show();
	}

}
