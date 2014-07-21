/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DashBoardActivity extends Activity  {
 
    static final String EXTRA_MAP = "map";
 
    static final LauncherIcon[] ICONS = {
            new LauncherIcon(R.drawable.community, "New MapMe", "metro.png"),
            new LauncherIcon(R.drawable.mymapme, "My MapMe", "rer.png"),
            new LauncherIcon(R.drawable.invite, "Partecipate", "bus.png"),
            new LauncherIcon(R.drawable.profile2, "Profile", "noctilien.png"),
            new LauncherIcon(R.drawable.settings, "Settings", "noctilien.png"),
            new LauncherIcon(R.drawable.logout2, "Logout", "noctilien.png"),
            new LauncherIcon(R.drawable.profile2, "Profile", "noctilien.png"),
            new LauncherIcon(R.drawable.settings, "Settings", "noctilien.png"),
            new LauncherIcon(R.drawable.logout2, "Logout", "noctilien.png")
    };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
 
        GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
        gridview.setAdapter(new ImageAdapter(this));
 
        // Hack to disable GridView scrolling
        gridview.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
    }
 
    static class LauncherIcon {
        final String text;
        final int imgId;
        final String map;
 
        public LauncherIcon(int imgId, String text, String map) {
            super();
            this.imgId = imgId;
            this.text = text;
            this.map = map;
        }
 
    }
 
    static class ImageAdapter extends BaseAdapter {
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
 
        static class ViewHolder {
            public ImageView icon;
            public TextView text;
        }
 
        // Create a new ImageView for each item referenced by the Adapter
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
}
