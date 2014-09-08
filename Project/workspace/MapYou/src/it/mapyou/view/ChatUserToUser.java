/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatUserToUser extends Activity{
	
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatusertouser);
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("user")){
			user = (User) b.getSerializable("user");
			if(user!=null){
				TextView n= (TextView) findViewById(R.id.textViewNickname);
				TextView e= (TextView) findViewById(R.id.textViewEmail);
				e.setText(user.getEmail());
				n.setText(user.getEmail());
			}
		}
	}
	
}
