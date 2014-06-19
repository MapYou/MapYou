/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Forgot extends Activity {
	
	
	private EditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		email= (EditText) findViewById(R.id.email_forgot);
		
		
	}

}
