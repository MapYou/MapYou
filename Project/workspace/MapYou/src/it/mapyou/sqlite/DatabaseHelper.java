/**
 * 
 */
package it.mapyou.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String NAME_DB="mapYou.db";


	// Name for tables
	public static final String MAPME="mapme";
	public static final String MAPPING="mapping";
	public static final String USER="user";
	public static final String SEGMENT="segment";
	public static final String PARTECIPATION="partecipation";

	// User Items
	public static final String ID_USER="id"; 
	public static final String NICKNAME="nickname";
	public static final String FIRSTANAME="firstname";
	public static final String LASTNAME="lastname";
	public static final String EMAIL="email";
	public static final String PASSWORD="password";

	// Mapme Items
	public static final String ID_MAPME="id";
<<<<<<< HEAD
	public static final String ID_USER_ADMIN="administrator";
=======
	public static final String ID_USER_ADMIN="idUser";
>>>>>>> d33f0a58dc6e5fbd532102d91e80e59a1b94405c


	// Mapping Items
	public static final String ID_MAPPING="id";
	public static final String ID_USER_M="idUserM";
	public static final String ID_MAPME_M="idMapmeM";

	// Segment Items
	public static final String ID_SEGMENT="id";
	public static final String START_LAT="startLat";
	public static final String END_LAT="endLat";
	public static final String START_LON="startLon";
	public static final String END_LON="endLon";
	public static final String LENGHT="lenght";
	
<<<<<<< HEAD
<<<<<<< HEAD
	

=======
=======
>>>>>>> d33f0a58dc6e5fbd532102d91e80e59a1b94405c
	// Partecipation
	public static final String ID_PARTECIPATION="id";
	public static final String ID_USER_PARTECIPATION="idUserP";
	public static final String ID_MAPME_PARTECIPATION="idMapmeP";
	public static final String ACCEPTANCE_PARTECIPATION="acceptance";
	public static final String REQUEST_PARTECIPATION="request";
	
<<<<<<< HEAD
>>>>>>> d33f0a58dc6e5fbd532102d91e80e59a1b94405c
=======
>>>>>>> d33f0a58dc6e5fbd532102d91e80e59a1b94405c
	//Constant
	public static final String CONSTANTS="ON DELETE CASCADE ON UPDATE CASCADE";


	private static final String USER_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.USER + " (" 
			+ DatabaseHelper.ID_USER+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.NICKNAME+ " text not null, "
			+ DatabaseHelper.PASSWORD+ " text not null, "
			+ DatabaseHelper.EMAIL+ " text not null, "
			+ DatabaseHelper.FIRSTANAME + " text, "
			+ DatabaseHelper.LASTNAME + " text);";

	private static final String MAPME_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.MAPME + " (" 
			+ DatabaseHelper.ID_MAPME+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ID_USER_ADMIN+ " int not null, "
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPME+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+") "+DatabaseHelper.CONSTANTS+");";


	private static final String MAPPING_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.MAPPING + " (" 
			+ DatabaseHelper.ID_MAPPING+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ID_USER_M+ " int not null, "
			+ DatabaseHelper.ID_MAPME_M+ " int not null, "
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPPING+") REFERENCES "+DatabaseHelper.SEGMENT+" ("+DatabaseHelper.ID_SEGMENT+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPME_M+") REFERENCES "+DatabaseHelper.MAPME+" ("+DatabaseHelper.ID_MAPME+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ID_USER_M+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+")  "+DatabaseHelper.CONSTANTS+");";


	private static final String SEGMENT_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.SEGMENT + " (" 
			+ DatabaseHelper.ID_SEGMENT+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.START_LAT+ " double not null, "
			+ DatabaseHelper.END_LAT+ " double not null, "
			+ DatabaseHelper.START_LON+ " double not null, "
			+ DatabaseHelper.END_LON+ " double not null, "
			+ DatabaseHelper.LENGHT+ " double not null);";
	 
	private static final String PARTECIPATION_TAB_CREATE = "CREATE TABLE IF NOT EXISTS " 
			+ DatabaseHelper.PARTECIPATION + " (" 
			+ DatabaseHelper.ID_PARTECIPATION+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ DatabaseHelper.ID_USER_PARTECIPATION+ " int not null, "
			+ DatabaseHelper.ID_MAPME_PARTECIPATION+ " int not null, "
			+ DatabaseHelper.ACCEPTANCE_PARTECIPATION+ " int not null DEFAULT 0, "
			+ DatabaseHelper.REQUEST_PARTECIPATION+ " int not null DEFAULT 0, "
			+ " FOREIGN KEY ("+DatabaseHelper.ID_MAPME_PARTECIPATION+") REFERENCES "+DatabaseHelper.MAPME+" ("+DatabaseHelper.ID_MAPME+") "+DatabaseHelper.CONSTANTS+","
			+ " FOREIGN KEY ("+DatabaseHelper.ID_USER_PARTECIPATION+") REFERENCES "+DatabaseHelper.USER+" ("+DatabaseHelper.ID_USER+") "+DatabaseHelper.CONSTANTS+");";

	public DatabaseHelper(Context context){
		super(context, NAME_DB, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_TAB_CREATE);
		db.execSQL(MAPME_TAB_CREATE);
		db.execSQL(SEGMENT_TAB_CREATE);
		db.execSQL(MAPPING_TAB_CREATE);
		db.execSQL(PARTECIPATION_TAB_CREATE);

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);

	}
	@Override
	public void onOpen(SQLiteDatabase db) {
		if(!db.isReadOnly())
			db.execSQL("PRAGMA foreign_keys=ON;");
	}
}

