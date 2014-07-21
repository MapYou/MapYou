/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import it.mapyou.model.Route;
import it.mapyou.model.StartPoint;
import it.mapyou.model.User;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeSecondTab_User extends Activity {

	private MapMe mapme;
	private List<Mapping> mapping;
	private Dialog sendDialog;
	private static final int SEND_DIALOG = 1;
	private Activity act;
	private View sendView;
	private EditText ed;
	private Random r;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		act = this;
		r = new Random();
		GridView gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		mapme = (MapMe) getIntent().getExtras().get("mapme");
		mapping = mapme.getMapping();
		UserAdapter adpter = new UserAdapter(this, R.layout.user_profile_mapme_grid, mapping);
		gridview.setAdapter(adpter);

		sendDialog();

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Mapping m = mapping.get(position);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
//			    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//			    if (prev != null) {
//			        ft.remove(prev);
//			    }
			    ft.addToBackStack(null);

			    UserDialogOnMapMe uf = UserDialogOnMapMe.getInstance();
			    uf.changeMapping(m);
			    uf.show(ft, "dialog");
			}
		});

		Button send = (Button) findViewById(R.id.buttonSendPartecipation);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(SEND_DIALOG);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SEND_DIALOG:
			return sendDialog;

		default:
			return null;
		}
	}

	private void sendDialog(){

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Insert nickname");
		builder.setCancelable(true);
		LayoutInflater inflater = act.getLayoutInflater();
		sendView = inflater.inflate(R.layout.send_partecipation_dialog, null);
		ed = (EditText)sendView.findViewById(R.id.editTextNickname);
		builder.setView(sendView);
		builder.setPositiveButton("Send invite", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String t = ed.getText().toString();
				if(t!=null && t.length()>0){
					Toast.makeText(getApplicationContext(), "Invito corretto", 4000).show();
					User u = new User(t, "email@email.com");
					Mapping m = new Mapping();
					m.setUser(u);
					Route rr = new Route();
					StartPoint sp= new StartPoint();
					EndPoint ep = new EndPoint();
					sp.setLatitude(45.4640704);
					sp.setLongitude(9.1719064);
					ep.setLatitude(45.070139);
					ep.setLongitude(7.6700892);
					rr.setStartPoint(sp);
					rr.setEndPoint(ep);
					m.setRoute(rr);
					mapping.add(m);
				}else
					Toast.makeText(getApplicationContext(), "Please insert correct nickname.", 4000).show();
				ed.setText("");
				
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		sendDialog = builder.create();

	}
	
}
