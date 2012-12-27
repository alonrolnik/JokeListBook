package cse.bgu.ex5.jokelistbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewJoke extends AcbWithMenu {

	ManipulateDB mydb;
	Joke joke;
	EditText etJoke;
	Button like;
	Button dislike;
	Button del;
	Button done;
	private boolean flagDel = false;
	
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

		mydb = new ManipulateDB(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_joke);
		Intent intent = getIntent();
		mydb.open();
		joke = mydb.getJoke(intent.getLongExtra(JokesList.KEY, -1));
		mydb.close();
		etJoke = (EditText) findViewById(R.id.etView);
		etJoke.setText(joke.getJoke());
	}
	
	public void onButtonClicked(View view){
		
		if(view.getId()!= R.id.bDone && flagDel)
		        Toast.makeText(getApplicationContext(), "you cannot change after delete press done", 
						Toast.LENGTH_SHORT).show();
		else{
				switch(view.getId()){
				case R.id.bLike:
					joke.setLikes("like");
			        Toast.makeText(getApplicationContext(), "like", 
			        					Toast.LENGTH_SHORT).show();
			        updateDB(joke);
					break;
				case R.id.bDislike:
					joke.setLikes("dislike");
			        Toast.makeText(getApplicationContext(), "dislike", 
			        					Toast.LENGTH_SHORT).show();
			        updateDB(joke);
			        break;
				case R.id.bDel:
			        Toast.makeText(getApplicationContext(), "you chose to delete press done!", 
			                Toast.LENGTH_SHORT).show();
			        mydb.open();
			        mydb.deleteJoke(joke);
			        mydb.close();
					etJoke.setText("");
					this.flagDel=true;
					break;
				case R.id.bDone:
					Intent intent = new Intent(this, cse.bgu.ex5.jokelistbook.JokesList.class);
		            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				default:
					break;
				}
		}
	}
	
	private void updateDB(Joke joke){
        mydb.open();
        mydb.updateJoke(joke);
        mydb.close();
	}
}

