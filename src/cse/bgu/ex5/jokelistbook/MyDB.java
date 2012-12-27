package cse.bgu.ex5.jokelistbook;

import android.provider.BaseColumns;

public class MyDB{
	/*
	 * Create private constructor to prevent instance of this class
	 */
	private MyDB() {}
	public static abstract class JokesTable implements BaseColumns{

	    public static final String TABLE_NAME = "jokes_table";
	    public static final String COLUMN_NAME_JOKES = "jokes";
	    public static final String COLUMN_NAME_CREATOR = "creator";
	    public static final String COLUMN_NAME_DATE = "date";	
	    public static final String COLUMN_NAME_LIKE = "like";
	}
}





