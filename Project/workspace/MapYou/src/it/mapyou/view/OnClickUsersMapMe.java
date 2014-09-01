package it.mapyou.view;

import it.mapyou.model.MappingUser;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnClickUsersMapMe implements OnItemClickListener  {

	private Activity act;
	private List<MappingUser> map;
	
	public OnClickUsersMapMe(Activity a, List<MappingUser> map) {
		this.act=a;
		this.map = map;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position,long id) {
		Bundle b = new Bundle();
		b.putParcelable("mapping", map.get(position));
		Intent i = new Intent(act, UserOnMapMe.class);
		i.putExtras(b);
		act.startActivity(i);
	}


}
