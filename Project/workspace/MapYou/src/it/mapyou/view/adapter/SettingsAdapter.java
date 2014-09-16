/**
 * 
 */
package it.mapyou.view.adapter;

import it.mapyou.view.Setting;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SettingsAdapter extends BaseAdapter{

	private Setting[] settings;
	private Activity act;
	
	/**
	 * @param act
	 * @param settings
	 */
	public SettingsAdapter(Activity act, Setting... settings) {
		super();
		this.act = act;
		this.settings = settings;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return settings.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return settings[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), settings[position].getIdView(), null);
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				settings[position].performeSetting();
			}
		});
		
		return convertView;
	}

}
