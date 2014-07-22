package it.mapyou.view;

import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnClickUsersMapMe implements OnItemClickListener  {

	private Activity act;
	private List<Mapping> map;
	private MapMe mapme;
	
	public OnClickUsersMapMe(Activity a, List<Mapping> map, MapMe mapme) {
		this.act=a;
		this.map = map;
		this.mapme = mapme;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position,long id) {
		Bundle b = new Bundle();
		b.putParcelable("mapme", mapme);
		b.putParcelableArrayList("mapping", (ArrayList<? extends Parcelable>) mapme.getMapping(map.get(position).getUser()));
		Intent i = new Intent(act, UserOnMapMe.class);
		i.putExtras(b);
		act.startActivity(i);
	}


}
