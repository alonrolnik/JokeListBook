package cse.bgu.ex5.jokelistbook;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JokesList extends AcbWithMenu {

	public static String KEY = "jokeIndex";
	private ListView listView;
	private ManipulateDB mydb;
	private ArrayList<Joke> jokeList;
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sharedPref = getSharedPreferences(MY_SHRD_PREF, Context.MODE_PRIVATE);
		boolean exitApp = sharedPref.getBoolean(EXIT_KEY, false);
		if(exitApp){
        	SharedPreferences.Editor editor = sharedPref.edit();
        	editor.putBoolean(EXIT_KEY, false);
        	editor.commit();
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jokes_list);
		mydb = new ManipulateDB(this);
	 	mydb.open();
		jokeList = (ArrayList<Joke>) mydb.getAllJokes();
		mydb.close();
		
		ArrayAdapter<Joke> adapter = new ArrayAdapter<Joke>(this,
									 android.R.layout.simple_list_item_1,
									 jokeList);
		listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, 
                int position, long id) {   
				Intent intent = new Intent (parent.getContext(), cse.bgu.ex5.jokelistbook.ViewJoke.class);
				intent.putExtra(KEY, jokeList.get(position).getId());
	        //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    		startActivity(intent);
	        }
		}
		);
	    listView.setAdapter(adapter);

		//init_test();
	}

	private void init_test() {
		// TODO Auto-generated method stub
 	   Joke joke1 = new Joke("Hi my name is Bob ha ha ha" , "Alon", "01/01/2012", "like");
 	   Joke joke2 = new Joke("Hello World", "Tal", "01/01/2011", "neither");
 	   Joke joke3 = new Joke("Hello Droid", "Alon", "01/01/2012", "dislike");
 	   Joke joke4 = new Joke("Hello JB", "Alon", "01/01/2012", "like");
 	   
 	   mydb.open();
 	   mydb.createJoke(joke1);
 	   mydb.createJoke(joke2);
 	   mydb.createJoke(joke3);
 	   mydb.createJoke(joke4);
 	   mydb.close();
	}
}