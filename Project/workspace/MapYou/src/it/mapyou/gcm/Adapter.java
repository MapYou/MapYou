package it.mapyou.gcm;

import it.mapyou.R;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
 

public class Adapter  extends BaseAdapter {

		Activity act;

		private List<User> notes= new ArrayList<User>();
		private User user;

		public Adapter(Activity activity) {
			act=activity;
		}
		
		@Override
		public int getCount() {

			return notes.size();
		}

		@Override
		public Object getItem(int position) {

			return notes.get(position);
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		public void addItem(User n){
			notes.add(n);
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

//
//			if(convertView==null){
//				convertView=View.inflate(parent.getContext(), R.layout.sendper, null);
//			}
//			//	TextView title= (TextView) convertView.findViewById(R.id.textViewDateCustom);
//			TextView title= (TextView) convertView.findViewById(R.id.textView1);
//			TextView num= (TextView) convertView.findViewById(R.id.textView2);
//			
//			user=notes.get(position);
//
//		//	title.setText(user.getName());
//			num.setText(user.getEmail());
//		 
		 

			return convertView;
		} 
		 

	}
 
