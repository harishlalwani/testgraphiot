package com.iot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends AppCompatActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public class SendDataAsync extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {
            try{

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://testgraphiot.herokuapp.com/randgraph/");

                try {

                    Random r = new Random();
                    int i1 = r.nextInt(80 - 65) + 100;
                    Log.e("event", i1+"");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("no", i1+""));

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){ Log.e("Error", e.toString()); }
            return null;
        }
    }

    public void onButtonStart(View view) {
        Toast.makeText(this, "Logging Started", Toast.LENGTH_LONG).show();
        TimerTask task=new TimerTask() {

            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        new SendDataAsync().execute();
                    }
                });
            }
        };
        timer=new Timer();
        timer.scheduleAtFixedRate(task, 100, 100);
    }



}
