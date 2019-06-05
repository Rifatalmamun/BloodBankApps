package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class currentLocation extends AppCompatActivity {

    private SearchView searchView;

    private ListView listView;
    private String[] cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        searchView=(SearchView)findViewById(R.id.searchView_id);

        listView=(ListView)findViewById(R.id.currentLocationListView_id);
        cityName=getResources().getStringArray(R.array.DistrictArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(currentLocation.this,R.layout.currentlocationsamplelistview,R.id.currentLocationSampleTextView_id,cityName);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = cityName[position];

                if(!value.isEmpty()){

                    Intent intent = new Intent(currentLocation.this,BeDonorActivity.class);

                     intent.putExtra("selectedCity",value);

                    /*Info info = new Info(value);
                    info.setCity(value);*/

                    startActivity(intent);
                    //Toast.makeText(currentLocation.this, "city: "+value, Toast.LENGTH_SHORT).show();

                    finish();
                }





            }
        });


    }
}
