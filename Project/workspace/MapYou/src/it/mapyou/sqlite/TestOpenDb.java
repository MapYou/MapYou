/**
 * 
 */
package it.mapyou.sqlite;

import java.util.ArrayList;

import it.mapyou.R;
import it.mapyou.model.User;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
 

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class TestOpenDb extends Activity {
	
	private DatabaseCreator db_map_you;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map_you_main);
		
		db_map_you= new DatabaseCreator(getApplicationContext());
		db_map_you.open();
		
		User u= new User();
		u.setEmail("@");
		u.setFirstname("Peppe");
		u.setLastname("Fusco");
		u.setPassword("1234");
		u.setNickname("peppeCix");
		db_map_you.insertUser(u);
		
		//retrieve
		
		ArrayList<User> users= db_map_you.getAllUser();
		Toast.makeText(getApplicationContext(), users.get(0).getFirstname(), 7000).show();
	}

}
