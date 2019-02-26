package croom.konekom.in.feederfox.network;


import android.os.AsyncTask;
import croom.konekom.in.feederfox.listner.NetworkResponseListener;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchData extends AsyncTask<String, Void, String> {
    NetworkResponseListener nrl;
    URL url;
    ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> resultHashMap;


    boolean searchForIds = false;
    JSONObject json = new JSONObject();

    public void setNrl(NetworkResponseListener nrl) {
        this.nrl = nrl;
    }


    public void setURL(String url) throws MalformedURLException {
        this.url = new URL(url);
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        nrl.preRequest();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String fetchedData = "";
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                fetchedData = sb.toString();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(fetchedData.charAt(0)!='['){
                fetchedData="["+fetchedData+"]";
            }
            return fetchedData;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);
        //Log.d("Suck u", "Suck u" + strings.size());
        nrl.postRequest(strings);

    }}