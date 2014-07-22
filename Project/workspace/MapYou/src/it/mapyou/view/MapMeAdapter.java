/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MapMe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeAdapter extends BaseAdapter {
 
    private Activity activity;
    private List<MapMe> data;
    private static LayoutInflater inflater=null;
    private List<Integer> numUsr;
 
    public MapMeAdapter(Activity a, List<MapMe> d, List<Integer> numUsr) {
        activity = a;
        data=d;
        this.numUsr = numUsr;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.mapme_list_row, null);

        MapMe m = data.get(position);
        TextView admin = (TextView) vi.findViewById(R.id.admin);
        TextView numusr = (TextView) vi.findViewById(R.id.num_users);
        TextView name = (TextView) vi.findViewById(R.id.mapmename);
        TextView sa = (TextView) vi.findViewById(R.id.textViewSA);
        TextView ea = (TextView) vi.findViewById(R.id.textViewEA);
        admin.setText("Admin: "+m.getAdministrator().getNickname());
        name.setText(m.getName());
        sa.setText(m.getStartAddress());
        ea.setText(m.getEndAddress());
        numusr.setText(String.valueOf(numUsr.get(position))+" / "+String.valueOf(m.getMaxNumUsers()));
        return vi;
    }
}
