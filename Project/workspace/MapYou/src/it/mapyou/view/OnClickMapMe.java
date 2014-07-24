/**
 * 
 */
package it.mapyou.view;

import it.mapyou.model.MapMe;
import it.mapyou.util.UtilAndroid;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class OnClickMapMe implements OnItemClickListener  {

	private Activity act;
	private List<MapMe> mapmes;
	private static MapMe mapme;
	
	public OnClickMapMe(Activity a, List<MapMe> map) {
		this.act=a;
		this.mapmes = map;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position,long id) {
		mapme = mapmes.get(position);
		Intent i = new Intent(act, MapMeLayoutHome.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Bundle b = new Bundle();
		b.putParcelable("mapme", mapme);
		i.putExtras(b);
		act.startActivity(i);	 
	}
	
	/**
	 * @return the mapme
	 */
	public static MapMe getMapme() {
		return mapme;
	}
 

}