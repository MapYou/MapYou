package it.mapyou.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnClickUsersMapMe implements OnItemClickListener  {

	 
	private SharedPreferences sp;
	Activity act;


	public OnClickUsersMapMe(  Activity a) {
		 
		this.act=a;
		this.sp=PreferenceManager.getDefaultSharedPreferences(act);


	}

	@SuppressWarnings("deprecation")
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position,long id) {

//		final Item i = items.get(position);
//		final String link= i.getLink();
//		final String title= i.getTitle();
//		final String img_link=i.getImg();
//
//		AlertDialog alert2= new AlertDialog.Builder(view.getContext()).create();
//		alert2.setTitle("Don Aurelio's Country Fans");
//		alert2.setMessage("\n"+title+"\n");
//		alert2.setIcon(R.drawable.ic_launcher);		
//
//		alert2.setButton2("Continua a leggere...", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//
//				Editor editor= sp.edit();
//				editor.putString("title", title);
//				editor.putString("imgLink", img_link);
//				editor.putString("link", link);
//				editor.commit();
//				
//				Intent i = new Intent(act, Article.class);
//				act.startActivity(i);
//			}
//		});


		//alert2.show();
	}


}
