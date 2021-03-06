package croom.konekom.in.feederfox;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import croom.konekom.in.feederfox.adapter.PoliticianAdapter;
import croom.konekom.in.feederfox.constant.Config;
import croom.konekom.in.feederfox.database.Politicians;
import croom.konekom.in.feederfox.listner.NetworkResponseListener;
import croom.konekom.in.feederfox.model.Politician;
import croom.konekom.in.feederfox.network.FetchData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class MainActivity extends AppCompatActivity implements NetworkResponseListener {
    private Politicians politicians;
    private ListView listView;
    private PoliticianAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private FetchData fetchData;
    private ProgressBar mProgressBar;
    private ArrayList<Politician> politiciansList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= findViewById(R.id.expandableListView);
        politiciansList = new ArrayList<>();
        politicians = new Politicians(MainActivity.this);

        expandableListAdapter = new PoliticianAdapter( politiciansList,this);
        listView.setAdapter(expandableListAdapter);
        mProgressBar=findViewById(R.id.progress_bar);
        fetchData = new FetchData();
        try {
            fetchData.setNrl(MainActivity.this);
            fetchData.setURL(Config.URL);
            fetchData.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        " List Expanded.",

                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,PoliticianView.class);
//intent.putExtra("Politician",politiciansList.get(groupPosition));
                PoliticianView.setPolitician(politiciansList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void preRequest() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postRequest(String result) {
        mProgressBar.setVisibility(View.GONE);
        Log.d("Result",result);
        parseData(result);
    }

    public void parseData(String result) {
        try {
            JSONArray politicianArray = new JSONArray(result).getJSONObject(0).getJSONArray("Politician_data");
            for(int i=0;i<politicianArray.length();i++){
                JSONObject politicianObject = politicianArray.getJSONObject(i);
                Politician politician = new Politician(politicianObject.getInt("id"),politicianObject.getString("Name"),
                        politicianObject.getString("Image"),politicianObject.getJSONArray("Politician_Comments"),politicianObject.getString("Educational_Qualification")
                ,politicianObject.getString("Family_History"),politicianObject.getString("video_link"));
                politicians.insertPolitician(politician.getId(),politician.getName(),politician.getImage_URL());
                politiciansList.add(politician);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("sixe",politiciansList.size()+"");
        expandableListAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this,"Database Stored of size "+politicians.getAllPoliticians().size(),Toast.LENGTH_SHORT).show();
    }
}
