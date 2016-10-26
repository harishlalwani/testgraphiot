package com.iot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

                /*HttpClient httpclient = new DefaultHttpClient();
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
                }*/

                Random r = new Random();
                int i1 = r.nextInt(80 - 65) + 100;

                String link="https://testgraphiot.herokuapp.com/randgraph/";
                String data  = URLEncoder.encode("no", "UTF-8") + "=" + URLEncoder.encode(i1+"", "UTF-8");


                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }

                Log.d("Loggs:",sb.toString() );

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
