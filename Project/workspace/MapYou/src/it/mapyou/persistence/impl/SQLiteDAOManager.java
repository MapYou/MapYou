
/**
 * 
 */
package it.mapyou.persistence.impl;

import it.mapyou.persistence.DAOManager;
import it.mapyou.persistence.MapMe_DAO;
import it.mapyou.persistence.Partecipation_DAO;
import it.mapyou.persistence.Point_DAO;
import it.mapyou.persistence.User_DAO;
import it.mapyou.sqlite.DatabaseHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SQLiteDAOManager extends DAOManager {

	private static SQLiteDAOManager instance;
	private SQLiteMapMeDAO sql_mapme;
	private SQLiteUserDAO sql_user;
	private SQLiteDatabase db;
	private DatabaseHelper conn;


	private SQLiteDAOManager(Context c){


		conn=new DatabaseHelper(c);
	}

	/**
	 * @return the instance
	 */
	public static SQLiteDAOManager getInstance(Context c) {
		if(instance == null)
			instance = new SQLiteDAOManager(c);
		return instance;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#getUserDAO()
	 */
	@Override
	public User_DAO getUserDAO() {
		// TODO Auto-generated method stub
		return sql_user;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#getMapMeDAO()
	 */
	@Override
	public MapMe_DAO getMapMeDAO() {
		// TODO Auto-generated method stub
		return sql_mapme;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#getPointDAO()
	 */
	@Override
	public Point_DAO getPointDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#commit()
	 */
	@Override
	public boolean commit() {
		// TODO Auto-generated method stub
		try {
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#rollback()
	 */
	@Override
	public boolean rollback() {
		// TODO Auto-generated method stub
		try {
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#connect()
	 */
	@Override
	public boolean connect() {

		try {
			db=conn.getWritableDatabase();
			sql_mapme = new SQLiteMapMeDAO(db);
			sql_user = new SQLiteUserDAO(db);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return the db
	 */
	public SQLiteDatabase getDb() {
		return db;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#close()
	 */
	@Override
	public boolean close() {

		try {

			conn.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#delete()
	 */
	@Override
	public boolean delete() {
		try{
			conn.delete(db);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#getPartecipationDAO()
	 */
	@Override
	public Partecipation_DAO getPartecipationDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOManager#updateDB()
	 */
	@Override
	public void updateDB() {
		// TODO Auto-generated method stub
		//conn.onDowngrade(db, db.getVersion(), db.getVersion()+1);
		//conn.onUpgrade(db, db.getVersion(), db.getVersion()+1);
	}

}
