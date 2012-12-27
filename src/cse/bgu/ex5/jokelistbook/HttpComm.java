package cse.bgu.ex5.jokelistbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpComm{
	static final String url1 = "http://www.jokesareawesome.com/rss/random/";

	public static void getHttpResponse(Handler handler ) {
		// TODO Auto-generated method stub
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpRequestBase httpRequest = new HttpGet(url1);
	HttpResponse response;
	
	Message msg = handler.obtainMessage();
	Bundle bundle = new Bundle();
	
	try {
		response = httpClient.execute(httpRequest);
		String res = resualtToString(response);	
		bundle.putBoolean("success", true);
		bundle.putString("result", res);
	} catch (ClientProtocolException e) {
		bundle.putBoolean("success", false);
	} catch (IOException e) {
		bundle.putBoolean("success", false);
	}
	msg.setData(bundle);
	handler.sendMessage(msg);
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
