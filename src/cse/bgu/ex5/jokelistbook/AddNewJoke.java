package cse.bgu.ex5.jokelistbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNewJoke extends AcbWithMenu {

	ManipulateDB mydb;
	EditText etJoke;
	EditText etData;
	EditText etAuthor;
	  
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sharedPref = getSharedPreferences(MY_SHRD_PREF, Context.MODE_PRIVATE);
		boolean exitApp = sharedPref.getBoolean(EXIT_KEY, false);
		if(exitApp)
			finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_joke);
	}
	
	public void doneButton (View view){
		etJoke = (EditText) findViewById(R.id.etJoke);
		etData = (EditText) findViewById(R.id.etDate);
		etAuthor = (EditText) findViewById(R.id.etAuthor);

		Joke newJoke = new Joke();
		mydb = new ManipulateDB(this);
		newJoke.setJoke(etJoke.getText().toString());
		newJoke.setAuthor(etAuthor.getText().toString());
		newJoke.setDate(etData.getText().toString());
		newJoke.setLikes("neither");
		mydb.open();
		mydb.createJoke(newJoke);
		mydb.close();
		Intent intent = new Intent(this, cse.bgu.ex5.jokelistbook.JokesList.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
