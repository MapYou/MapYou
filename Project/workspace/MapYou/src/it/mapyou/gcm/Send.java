//package it.mapyou.gcm;
//
//import it.mapyou.R;
//import it.mapyou.model.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.GridView;
//
// 
//
//public class Send extends Activity {
//	
//	
//	private GridView grid;
//	private List<User> list;
//	private Activity ac;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
////		 
////		super.onCreate(savedInstanceState);
////		setContentView(R.layout.send);
////		ac=this;
////		grid= (GridView) findViewById(R.id.gridView1);
////		
////		new Task().execute();
////	}
//	
//
////	class Task extends AsyncTask<Void, Void, List<User>>{
////
////		@Override
////		protected List<User> doInBackground(Void... params) {
////			
////			list= new ArrayList<User>();
////			list.add(new User("mar", "marcel", "APA91bFITssE_fo2EWhFGhQPhqLURVTFUc75b1jueq2LYa25UmNPjfuTBx1MR0pLxVaJ4R-Al_g7Q5tDv0WbCwsnGswRXAuzfkGcqHQ2q0O7hMqdCPfEfZjjLPagGfDGgN3jW7xnwCxd"));
////			list.add(new User("cix", "cix", "APA91bEctV6qRm2ZAUnoo2-GoNvcDzSW0ESAT--s50tMRG8CUzf34tpml4cufsKY5nm4fJo63I_2ys0OwTd9UCEY6NRGqd9ZU0zQZKkoud4b4FBYokHNIWO6RQ04ExtmRoyogUQVYJmskHgG5UJYhkjDR9VSZmLpzQ"));
////			
////			return list;
////		}
////		
////		@Override
////		protected void onPostExecute(List<User> result) {
////			super.onPostExecute(result);
////			
////			Adapter adapter= new Adapter(Send.this);
////
////			for(User n: result){
////				adapter.addItem(n);
////			}
////
////			grid.setAdapter(adapter);
////			grid.setOnItemClickListener(new OnClick(ac,result));
////		}
//	}
//}