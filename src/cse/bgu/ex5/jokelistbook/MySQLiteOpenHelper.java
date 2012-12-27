package cse.bgu.ex5.jokelistbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String TABLE_NAME_ENTRIES = MyDB.JokesTable.COLUMN_NAME_JOKES + COMMA_SEP +
		    										 MyDB.JokesTable.COLUMN_NAME_CREATOR + COMMA_SEP +
		    										 MyDB.JokesTable.COLUMN_NAME_DATE + MyDB.JokesTable.COLUMN_NAME_LIKE;
	
	private static final String SQL_CREATE_ENTRIES = "create table " + MyDB.JokesTable.TABLE_NAME + "(" +
	    											  MyDB.JokesTable._ID + " integer primary key autoincrement, " +
	    											  MyDB.JokesTable.COLUMN_NAME_JOKES + TEXT_TYPE + COMMA_SEP +
	    											  MyDB.JokesTable.COLUMN_NAME_CREATOR + TEXT_TYPE + COMMA_SEP +
	    											  MyDB.JokesTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP + 
	    											  MyDB.JokesTable.COLUMN_NAME_LIKE + TEXT_TYPE +
	    											  " text not null" +
	    											  " );";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jokes.db";

    //create helper object to manipulate database
	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
    @Override
	public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	}
    
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);	

	}
}
