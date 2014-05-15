

/**
 * 
 */
package it.mapyou.persistence.impl;

import it.mapyou.model.User;
import it.mapyou.persistence.User_DAO;
import it.mapyou.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SQLiteUserDAO implements User_DAO {


	private SQLiteDatabase db;

	public SQLiteUserDAO(SQLiteDatabase db) {
		this.db=db;

	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOSubjectModelInterface#selectByModelID(int)
	 */
	@Override
	public User selectByModelID(int modelID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(User n) {

		try {
			db.beginTransaction();
			ContentValues values= new ContentValues();
			//values.put(DatabaseHelper.ID_USER, n.getModelID());
			values.put(DatabaseHelper.NICKNAME, n.getNickname().toString());
			values.put(DatabaseHelper.PASSWORD, n.getPassword().toString());
			values.put(DatabaseHelper.EMAIL, n.getEmail().toString());
			values.put(DatabaseHelper.FIRSTANAME, n.getFirstname().toString());
			values.put(DatabaseHelper.LASTNAME, n.getLastname().toString());

			db.insert(DatabaseHelper.USER, null, values);
			db.setTransactionSuccessful();
			return true;

		} catch (Exception e) {
			db.endTransaction();
			return false;
		}
		finally{
			db.endTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(User t) {




		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#update(java.lang.Object)
	 */
	@Override
	public boolean update(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#selectAll()
	 */
	@Override
	public List<User> selectAll() {


		ArrayList<User> users= new ArrayList<User>();
		String query= "SELECT * FROM "+DatabaseHelper.USER+";";
		Cursor c=db.rawQuery(query, null);
		User user= new User();
		if(c.moveToFirst())
			while(!c.isAfterLast()){
				user.setNickname(c.getString(1));
				user.setPassword(c.getString(2));
				user.setEmail(c.getString(3));
				user.setFirstname(c.getString(4));
				user.setLastname(c.getString(5));
				users.add(user);
				c.moveToNext();
			}
		c.close();
		return users;

	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.User_DAO#selectByNickname(java.lang.String)
	 */
	@Override
	public User selectByNickname(String nickname) {

		db.beginTransaction();
		try {
			String query= "SELECT * FROM "+DatabaseHelper.USER+" WHERE "+DatabaseHelper.NICKNAME+"="+nickname+";";
			Cursor c=db.rawQuery(query, null);
			User user= new User();
			if(c.moveToFirst()){
				user.setModelID(c.getInt(0));
				user.setNickname(c.getString(1));
				user.setPassword(c.getString(2));
				user.setEmail(c.getString(3));
				user.setFirstname(c.getString(4));
				user.setLastname(c.getString(5));
				c.close();
				db.setTransactionSuccessful();
				return user;
			}else{
				db.endTransaction();
				return null;
			}

		} catch (Exception e) {
			db.endTransaction();
			return null;
		}
		finally{
			db.endTransaction();
		}

	}


	/* (non-Javadoc)
	 * @see it.mapyou.persistence.User_DAO#deleteByNickname(it.mapyou.model.User)
	 */
	@Override
	public boolean deleteByNickname(User user) {




		return false;
	}

 
 }