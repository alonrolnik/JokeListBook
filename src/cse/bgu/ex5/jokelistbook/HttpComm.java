package cse.bgu.ex5.jokelistbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpComm{
	static final String url1 = "http://www.jokesareawesome.com/rss/random/";
	static ManipulateDB mydb;

	public static void getHttpResponse(Handler handler, ManipulateDB mydb) throws XmlPullParserException {
		// TODO Auto-generated method stub
	//DefaultHttpClient httpClient = new DefaultHttpClient();
	//HttpRequestBase httpRequest = new HttpGet(url1);
	//HttpResponse response;
	
//	Message msg = handler.obtainMessage();
//	Bundle bundle = new Bundle();
	
	try {
	    URL url = new URL(url1);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();
	    InputStream stream = conn.getInputStream();
	    XmlJokesParser parser = new XmlJokesParser();
	    List<Joke> list = parser.parse(stream);
	   
		mydb.open();
		mydb.createJoke(list.get(0));
		mydb.close();

		//insert new joke to DB
		
	} catch (ClientProtocolException e) {
	//	bundle.putBoolean("success", false);
	} catch (IOException e) {
	//	bundle.putBoolean("success", false);
	}
//	msg.setData(bundle);
	//handler.sendMessage(msg);
}


private static String resualtToString(HttpResponse response) throws IOException {
	StringWriter writer = new StringWriter();
	Reader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	int r;
	while ((r = reader.read()) != -1) {
		writer.write(r);
	}
	return writer.toString();
}


}
