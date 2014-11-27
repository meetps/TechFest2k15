package org.iitb.techfest.techfest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends Activity {
    List<String[]> data;
    ArrayList<EventSummary> converted=new ArrayList<EventSummary>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new ReadTask().execute("http://home.iitb.ac.in/~mudeshi1209/includes/tf-event-2014.csv");
    }

    private class ReadTask extends AsyncTask<String, Integer, List<String[]>> {
        @Override
        protected List<String[]> doInBackground(String... url) {
            List<String[]> data = new ArrayList<String[]>();
            try {
                HttpConnection http = new HttpConnection();
                data = http.getCSV(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Download Debug", "progress:" + progress);
        }

        @Override
        protected void onPostExecute(List<String[]> result) {
            super.onPostExecute(result);
            data=result;
            startMainActivity();
        }
    }

    private void startMainActivity(){
        int i=0;
        data.remove(0);
        data.remove(0);

        for(String[] row : data){
            EventSummary es = new EventSummary(i,R.drawable.tf_icon,0,row[0],"arbit desc",row[4],row[2],row[3]);

            converted.add(es);

            i++;
        }

        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        intent.putExtra("events",converted);
        startActivity(intent);

        finish();
    }
}