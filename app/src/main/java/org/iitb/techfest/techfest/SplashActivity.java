package org.iitb.techfest.techfest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        loader=(IconProgressBar)findViewById(R.id.splash_loader);
        text = (TextView)findViewById(R.id.percent_text);

        new ReadTask().execute("https://drive.google.com/uc?execute=download&id=0B_6rvZNWXShpMi03TkR1MWxwUGM");
    }

    private class ReadTask extends AsyncTask<String, Integer, List<String[]>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loader = (IconProgressBar)findViewById(R.id.splash_loader);
            text = (TextView)findViewById(R.id.percent_text);

            Log.d("ProgressBar","onPreExecute : "+loader);
        }

        @Override
        protected List<String[]> doInBackground(String... url) {
            List<String[]> data = new ArrayList<String[]>();
            try {
                data = downloadCSV(url[0]);

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
                        data=parseCSV(in,in.getChannel().size());
                        Log.d("Cache Load","Loaded from app cache");
                    } catch (Exception e) {
                        Log.e("Cache Load",e.getMessage());

                        try {
                            InputStream asset = getAssets().open("events.csv");
                            data=parseCSV(asset,asset.available());
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
            Log.d("Download Debug", "progress:" + progress[0]);

            if(loader!=null)
                loader.updateProgress(progress[0]);

            text.setText("Loading events "+progress[0]+"%");
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

                data=parseCSV(iStream, urlConnection.getContentLength());
            } catch (Exception e) {
                Log.d("Exception while reading url", e.toString());
            } finally {
                if(iStream!=null) iStream.close();
                urlConnection.disconnect();

                return data;
            }
        }

        public List<String[]> parseCSV(InputStream in, long streamLength) throws IOException{
            List<String[]> data=new ArrayList<String[]>();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    in));

            long loaded=0;

            String line = "";
            while ((line = br.readLine()) != null) {
                loaded+=line.length();
                String[] next = line.split(";");
                Log.i("Download Debug","row recieved : "+next[0]);
                data.add(next);

                publishProgress((int)((double)loaded/streamLength*100));

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