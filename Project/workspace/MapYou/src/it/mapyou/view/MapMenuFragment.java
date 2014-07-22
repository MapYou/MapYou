/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMenuFragment extends Fragment{

	static final String EXTRA_MAP = "map";

	static final LauncherIcon[] ICONS = {
		new LauncherIcon(R.drawable.community, "New MapMe", NewMapMe.class),
		new LauncherIcon(R.drawable.mymapme, "My MapMe", YourMapMeActivity.class),
		new LauncherIcon(R.drawable.invite, "Invite your friends", null),
		new LauncherIcon(R.drawable.settings, "Settings", null),
		new LauncherIcon(R.drawable.logout2, "Logout", null)
	};

	static class LauncherIcon {
		String text;
		int imgId;
		Class<?> clazz;

		public LauncherIcon(int imgId, String text, Class<?> c) {
			this.imgId = imgId;
			this.text = text;
			this.clazz = c;
		}

		/**
		 * @return the clazz
		 */
		public Class<?> getClazz() {
			return clazz;
		}
		
		/**
		 * @return the imgId
		 */
		public int getImgId() {
			return imgId;
		}
		
		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}

	class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public LauncherIcon getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		class ViewHolder {
			public ImageView icon;
			public TextView text;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

				v = vi.inflate(R.layout.dashboard_icon, null);
				holder = new ViewHolder();
				holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
				holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			holder.icon.setImageResource(ICONS[position].imgId);
			holder.text.setText(ICONS[position].text);

			return v;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.dashboard, container, false);

		GridView gridview = (GridView) view.findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(getActivity()));
		
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Class<?> c = ICONS[position].getClazz();
				if(c!=null){
					getActivity().startActivity(new Intent(getActivity(), c));
				}
			}
		});
		return view;
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {  
		super.onActivityCreated(savedInstanceState);  


	}
}

