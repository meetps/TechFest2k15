package org.iitb.techfest.techfest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
                data = http.downloadCSV(url[0]);

                if(data!=null){
                    try {
                        PrintWriter out=new PrintWriter(openFileOutput("events.csv", Context.MODE_PRIVATE));
                        for(String[]  row : data){
                            for(int i=0; i<row.length; i++){
                                out.write(row[i]+(i+1==row.length?"":";"));
                            }
                            out.write('\n');
                        }

                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        Log.e("Cache Save",e.getMessage());
                    }
                } else {
                    try {
                        FileInputStream in = openFileInput("events.csv");
                        data=HttpConnection.parseCSV(in);
                        Log.d("Cache Load","Loaded from app cache");
                    } catch (Exception e) {
                        Log.e("Cache Load",e.getMessage());

                        try {
                            data=HttpConnection.parseCSV(getAssets().open("events.csv"));
                            Log.d("Cache Load","Loaded from assets");
                        } catch (IOException e1) {
                            Log.e("Cache Load", e1.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
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
            EventSummary es = new EventSummary(i,0,0,0,row[0],row[1],row[2],row[3],row[4],row[5]);

            converted.add(es);

            i++;
        }

        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        intent.putParcelableArrayListExtra("events",converted);
        startActivity(intent);

        finish();
    }
}