/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.sqlite.DatabaseHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SqlLyteDaoManager extends DAOManager {
	
	private SQLiteDatabase db;
	private DatabaseHelper conn;
	 
	public SqlLyteDaoManager() {
		
		
		 
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.Source#commit()
	 */
	@Override
	public boolean commit() {
		// TODO Auto-generated method stub
		return false;
	}
	 
	@Override
	public Source getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public User_DAO getUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public MapMe_DAO getMapMeDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public Notification_DAO getNotificationDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public Point_DAO getPointDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
