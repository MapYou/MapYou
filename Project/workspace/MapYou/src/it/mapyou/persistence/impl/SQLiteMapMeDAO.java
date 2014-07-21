
/**
 * 
 */
package it.mapyou.persistence.impl;

import it.mapyou.model.MapMe;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.persistence.MapMe_DAO;
import it.mapyou.sqlite.DatabaseHelper;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SQLiteMapMeDAO implements MapMe_DAO {
	

	private SQLiteDatabase db;
	
	public SQLiteMapMeDAO(SQLiteDatabase db){
		this.db = db;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOSubjectModelInterface#selectByModelID(int)
	 */
	@Override
	public MapMe selectByModelID(int modelID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(MapMe t) {
		// TODO Auto-generated method stub
		db.beginTransaction();
		try {
			String query= "SELECT "+DatabaseHelper.ID_USER+" FROM "+DatabaseHelper.USER+" WHERE "+DatabaseHelper.NICKNAME+"="+0+";";
		
			Cursor c=db.rawQuery(query, null);
			if(c.getCount()==1 && c.moveToFirst()){
				ContentValues values= new ContentValues();
				values.put(DatabaseHelper.ID_MAPME_M, c.getInt(0));
				c.close();
				db.insert(DatabaseHelper.MAPME, null, values);
				db.setTransactionSuccessful();
				return true;
			}
			else{
				c.close();
				db.endTransaction();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			db.endTransaction();
			return false;
		}finally{
			db.endTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(MapMe t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#update(java.lang.Object)
	 */
	@Override
	public boolean update(MapMe t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#selectAll()
	 */
	@Override
	public List<MapMe> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.MapMe_DAO#selectSegmentByUser(it.mapyou.model.User)
	 */
	@Override
	public List<Segment> selectSegmentByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.MapMe_DAO#selectByUser(it.mapyou.model.User)
	 */
	@Override
	public List<MapMe> selectByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
