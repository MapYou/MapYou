///**
// * 
// */
//package it.mapyou.sqlite;
//
//import it.mapyou.model.User;
//
//import java.util.ArrayList;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
///**
// * @author mapyou (mapyouu@gmail.com)
// *
// */
//public class DatabaseCreator {
//
//	private SQLiteDatabase db;
//	private DatabaseHelper conn;
//
//
//	public DatabaseCreator(Context c) {
//		conn=new DatabaseHelper(c);
//	}
//
//	/**
//	 * @return the db
//	 */
//	public SQLiteDatabase getDb() {
//		return db;
//	}
//	public void open(){
//		db=conn.getWritableDatabase();
//	}
//
//	public void close(){
//		conn.close();
//	}
//	
//	public void delete(){
//		conn.delete(db);
//	}
//
//	public void insertUser (User n){
//
//		ContentValues values= new ContentValues();
//		values.put(DatabaseHelper.NICKNAME, n.getNickname().toString());
//		values.put(DatabaseHelper.PASSWORD, n.getPassword().toString());
//		values.put(DatabaseHelper.EMAIL, n.getEmail().toString());
//		values.put(DatabaseHelper.FIRSTANAME, n.getFirstname().toString());
//		values.put(DatabaseHelper.LASTNAME, n.getLastname().toString());
//
//		db.insert(DatabaseHelper.USER, null, values);
//	}
//
//	public ArrayList<User> getAllUser (){
//
//		ArrayList<User> users= new ArrayList<User>();
//		String query= "SELECT * FROM "+DatabaseHelper.USER+";";
//		Cursor c=db.rawQuery(query, null);
//		User user= new User();
//		if(c.moveToFirst())
//			while(!c.isAfterLast()){
//				user.setNickname(c.getString(1));
//				user.setPassword(c.getString(2));
//				user.setEmail(c.getString(3));
//				user.setFirstname(c.getString(4));
//				user.setLastname(c.getString(5));
//				users.add(user);
//				c.moveToNext();
//			}
//		c.close();
//		return users;
//	}
//
//
//}