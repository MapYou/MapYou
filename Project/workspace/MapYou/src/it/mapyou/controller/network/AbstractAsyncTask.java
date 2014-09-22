/**
 * 
 */
package it.mapyou.controller.network;

import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z>{

	protected HashMap<String, String> parameters=new HashMap<String, String>();
	protected ProgressDialog p;
	protected Context act;

	public AbstractAsyncTask(Context act, String message) {
		this.act = act;
		p = new ProgressDialog(act);
		p.setMessage(message);
		p.setIndeterminate(false);
		p.setCancelable(true);
		p.setCanceledOnTouchOutside(true);
		p.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancel(true);
			}
		});
	}
	
	public AbstractAsyncTask(Context act) {
		this.act = act;
		p = new ProgressDialog(act);
		p.setMessage("");
		p.setIndeterminate(false);
		p.setCancelable(true);
		p.setCanceledOnTouchOutside(true);
		p.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancel(true);
			}
		});
	}
	
	public AbstractAsyncTask(Activity act, String message) {
		this.act = act;
		p = new ProgressDialog(act);
		p.setMessage(message);
		p.setIndeterminate(false);
		p.setCancelable(true);
		p.setCanceledOnTouchOutside(true);
		p.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancel(true);
			}
		});
	}
	
	public AbstractAsyncTask(Activity act) {
		this.act = act;
		p = new ProgressDialog(act);
		p.setMessage("");
		p.setIndeterminate(false);
		p.setCancelable(true);
		p.setCanceledOnTouchOutside(true);
		p.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancel(true);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected final void onPostExecute(Z result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		p.dismiss();
		newOnPostExecute(result);
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected final void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		p.show();
		if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
			UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			p.cancel();
			}
		else;
		
		newOnPreExecute();
	}
	
	protected void newOnPostExecute(Z result){
		
	}
	
	protected void newOnPreExecute(){
		
	}
}
