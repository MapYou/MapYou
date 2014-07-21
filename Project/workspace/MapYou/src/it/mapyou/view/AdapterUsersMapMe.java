//package it.mapyou.view;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.graphics.Typeface;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class AdapterUsersMapMe extends BaseAdapter {
//
//	private Activity act;
//
//	 
//	private ImageView img;
//
//	public AdapterUsersMapMe(Activity activity) {
//		this.act=activity;
//	 
//	}
//	@Override
//	public int getCount() {
//
//		return items.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//
//		return items.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//
//		return 0;
//	}
//
//	public void addItem(Item i){
//		items.add(i);
//	}
//
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		if(convertView==null){
//			convertView=View.inflate(parent.getContext(), R.layout.item, null);
//		}
//
//		TextView title= (TextView) convertView.findViewById(R.id.textViewTitle);
//		TextView preview= (TextView) convertView.findViewById(R.id.textViewPreview);
//		img = (ImageView) convertView.findViewById(R.id.imageView1);
//
////		item=items.get(position);
////
////		title.setText(item.getTitle());
////		preview.setText(item.getPreview());
////		img.setImageBitmap(item.getBitmap());
////		
//		return convertView;
//	} 
//	
//	public void changeFontText (TextView v ){ 
//		Typeface font = Typeface.createFromAsset(act.getAssets(), "emmasophia.ttf");
//		v.setTypeface(font);
//	}
//}
//
