/**
 * 
 */
package it.mapyou.view;

import java.util.ArrayList;
import java.util.List;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
				
				Intent i= new Intent(getActivity(), NewMapMe.class);
				startActivity(i);
				
			}
		}); 
		
		Button button2 = (Button) view.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				Intent i= new Intent(getActivity(), YourMapMeActivity.class);
				Bundle b = new Bundle();
				List<MapMe> l = new ArrayList<MapMe>();
				for(int k=1; k<7; k++)
					l.add(new MapMe("mapme_"+k));
				b.putParcelableArrayList("listmapme", (ArrayList<? extends Parcelable>) l);
				i.putExtras(b);
				startActivity(i);
				
			}
		}); 
		
		Button button3 = (Button) view.findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				Intent i= new Intent(getActivity(), DashBoardActivity.class);
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

