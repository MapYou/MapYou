/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.navigator.MapsProva;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMenuFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.mapme_menu, container, false);
		Button button = (Button) view.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				Intent i= new Intent(getActivity(), MapsProva.class);
				startActivity(i);
				
			}
		}); 
		return view;
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {  
		super.onActivityCreated(savedInstanceState);  

		
	}
}

