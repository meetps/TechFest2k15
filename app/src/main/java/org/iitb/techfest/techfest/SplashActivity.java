package org.iitb.techfest.techfest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends Activity {
    List<String[]> data;
    ArrayList<EventSummary> converted=new ArrayList<EventSummary>();
    IconProgressBar loader;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loader=new IconProgressBar(this, null);
        loader.setLayoutParams(new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.splash_logo_dimen),(int)getResources().getDimension(R.dimen.splash_logo_dimen)));
        text = (TextView)findViewById(R.id.percent_text);

        new ReadTask().execute("https://drive.google.com/uc?execute=download&id=0B_6rvZNWXShpMi03TkR1MWxwUGM");
    }

    private class ReadTask extends AsyncTask<String, Integer, List<String[]>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            ((LinearLayout)findViewById(R.id.splash_container)).addView(loader,0);
            text = (TextView)findViewById(R.id.percent_text);

            Log.d("ProgressBar","onPreExecute : "+loader);
        }

        @Override
        protected List<String[]> doInBackground(String... url) {
            List<String[]> data = new ArrayList<String[]>();
            int count;
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection urlConnection = null;

            try {
                URL csvUrl = new URL(url[0]);
                urlConnection = (HttpURLConnection) csvUrl.openConnection();
                urlConnection.setInstanceFollowRedirects(true);
                urlConnection.connect();

                Log.d("Download Debug","response: " + urlConnection.getResponseCode()+" -> "+urlConnection.getResponseMessage());
                input = urlConnection.getInputStream();

                output=openFileOutput("events.csv", Context.MODE_PRIVATE);

                byte downloadData[] = new byte[1024];

                long total = 0;

                while ((count = input.read(downloadData)) != -1) {
                    total += count;
                    publishProgress((int)((total*100/urlConnection.getContentLength())));
                    output.write(downloadData, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                deleteFile("events.csv");
            }

            try {
                publishProgress(20);
                data = parseCSV(openFileInput("events.csv"));
                publishProgress(100);
            } catch (Exception e){
                Log.e("Cache Load",e.getMessage());

                try {
                    publishProgress(20);
                    data=parseCSV(getAssets().open("events.csv"));
                    publishProgress(100);
                    Log.d("Cache Load","Loaded from assets");
                } catch (IOException e1) {
                    Log.e("Cache Load", e1.getMessage());
                }
            }

            return data;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Download Debug", "progress:" + progress[0]);

            if(loader!=null)
                loader.updateProgress(progress[0]);

            text.setText("Loading events " + progress[0] + "%");
        }

        @Override
        protected void onPostExecute(List<String[]> result) {
            super.onPostExecute(result);
            data=result;
            startMainActivity();
        }

        public List<String[]> downloadCSV(String csvUrl) throws IOException {
            List<String[]> data=null;
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(csvUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setInstanceFollowRedirects(true);
                urlConnection.connect();

                Log.d("Download Debug","response: " + urlConnection.getResponseCode()+" -> "+urlConnection.getResponseMessage());
                iStream = urlConnection.getInputStream();

                data=parseCSV(iStream);
            } catch (Exception e) {
                Log.d("Exception while reading url", e.toString());
            } finally {
                if(iStream!=null) iStream.close();
                urlConnection.disconnect();

                return data;
            }
        }

        public List<String[]> parseCSV(InputStream in) throws IOException{
            List<String[]> data=new ArrayList<String[]>();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    in));

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] next = line.split(";");
                Log.i("Download Debug","row recieved : "+next[0]);
                data.add(next);

            }
            Log.i("Download Debug","data size : "+data.size());

            in.close();

            return data;
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