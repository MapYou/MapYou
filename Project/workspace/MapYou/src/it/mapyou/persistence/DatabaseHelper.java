/**
 * 
 */
package it.mapyou.persistence;


import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String NAME_DB="mapYou.db";


	// Name for tables
	public static final String USER="user";
	public static final String MAPME="mapme";
	public static final String MAPPING="mapping";
	public static final String SEGMENT="segment";
	public static final String REGISTRATION_MAPME="registration";
	
	
	// User Items
	public static final String ID_USER="iduser"; 
	public static final String NICKNAME="nickname";
 

	// Mapme Items
	public static final String ID_MAPME="id";
	public static final String ADMINISTRATOR="administrator";
	public static final String NAME="name";
	public static final String NUM_MAX_USERS="numMaxUsers";
	public static final String STATE="state";
	public static final String SEGMENT_ON_MAPME="segment";


	// Mapping Items
	public static final String ID_MAPPING="id";
	public static final String ID_USER_M="idUserM";
	public static final String ID_MAPME_M="idMapmeM";
	public static final String LATITUDE_M="latitude_M";
	public static final String LONGITUDE_M="longitude_M";

	// Segment Items
	public static final String ID_SEGMENT="id";
	public static final String FIRST_LAT="firstlat";
	public static final String END_LAT="endLat";
	public static final String FIRST_LON="firstLon";
	public static final String END_LON="endLon";

	
	// Registration_on_mapme
	public static final String ID_REGISTRATION="idreg";
	public static final String ID_USER_REGISTRATION="idUserReg";
	public static final String ID_MAPME_REGISTRATION="idMapmeReg";
	 

	//Constant
	public static final String CONSTANTS="ON DELETE CASCADE ON UPDATE CASCADE";

	
	private static final String USER_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.USER + " (" 
			+ DatabaseHelper.ID_USER+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.NICKNAME+ " text not null UNIQUE ); ";
		 

	private static final String MAPME_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.MAPME + " (" 
			+ DatabaseHelper.ID_MAPME+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ADMINISTRATOR+ " int not null, "
			+ DatabaseHelper.NAME+ " text not null, "
			+ DatabaseHelper.NUM_MAX_USERS+ " int not null DEFAULT 10, "
			+ DatabaseHelper.STATE+ " text not null, "
			+ DatabaseHelper.SEGMENT_ON_MAPME+ " int not null, "
			+ " FOREIGN KEY ("+DatabaseHelper.SEGMENT_ON_MAPME+") REFERENCES "+DatabaseHelper.SEGMENT+" ("+DatabaseHelper.ID_SEGMENT+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ADMINISTRATOR+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+") "+DatabaseHelper.CONSTANTS+");";


	private static final String MAPPING_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.MAPPING + " (" 
			+ DatabaseHelper.ID_MAPPING+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ID_USER_M+ " int not null, "
			+ DatabaseHelper.ID_MAPME_M+ " int not null, "
			+ DatabaseHelper.LATITUDE_M+ " double not null, "
			+ DatabaseHelper.LONGITUDE_M+ " double not null, "
		
			+ " FOREIGN KEY ("+DatabaseHelper.ID_USER+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPME_M+") REFERENCES "+DatabaseHelper.MAPME+" ("+DatabaseHelper.ID_MAPME+") "+DatabaseHelper.CONSTANTS+",";
			


	private static final String SEGMENT_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.SEGMENT + " (" 
			+ DatabaseHelper.ID_SEGMENT+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.FIRST_LAT+ " double not null, "
			+ DatabaseHelper.END_LAT+ " double not null, "
			+ DatabaseHelper.FIRST_LON+ " double not null, "
			+ DatabaseHelper.END_LON+ " double not null); ";
			 
	 
	private static final String REGISTRATION_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.REGISTRATION_MAPME + " (" 
			+ DatabaseHelper.ID_REGISTRATION+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ID_USER_REGISTRATION+ " int not null, "
			+ DatabaseHelper.ID_MAPME_REGISTRATION+ " int not null, "
			 
			+ " FOREIGN KEY ("+DatabaseHelper.ID_USER_REGISTRATION+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPME_REGISTRATION+") REFERENCES "+DatabaseHelper.MAPME+" ("+DatabaseHelper.ID_MAPME+") "+DatabaseHelper.CONSTANTS+");";

	public DatabaseHelper(Context context){
		super(context, NAME_DB, null, 1);

	}
	
	public boolean delete (SQLiteDatabase db){
		return SQLiteDatabase.deleteDatabase(new File(db.getPath()));
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_TAB_CREATE);
		db.execSQL(MAPME_TAB_CREATE);
		db.execSQL(SEGMENT_TAB_CREATE);
		db.execSQL(MAPPING_TAB_CREATE);
		db.execSQL(REGISTRATION_TAB_CREATE);

	}
	
//	public void onDelete(SQLiteDatabase db) {
//		db.execSQL("DROP TABLE IF EXISTS "+USER+";");
//		db.execSQL("DROP TABLE IF EXISTS "+MAPME+";");
//		db.execSQL("DROP TABLE IF EXISTS "+SEGMENT+";");
//		db.execSQL("DROP TABLE IF EXISTS "+MAPPING+";");
//		db.execSQL("DROP TABLE IF EXISTS "+PARTECIPATION+";");
//
//	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		onCreate(db);
		
	

	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onDowngrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//delete(db);
			//onCreate(db);
	}
	
	 
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		if(!db.isReadOnly())
			db.execSQL("PRAGMA foreign_keys=ON;");
	}
 
}

