package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class districtList_For_BloodBankSearch extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private String[] cityName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list__for__blood_bank_search);

        searchView=(SearchView)findViewById(R.id.searchView_id);

        listView=(ListView)findViewById(R.id.districtListForBloodBankListView_id);
        cityName=getResources().getStringArray(R.array.DistrictArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(districtList_For_BloodBankSearch.this,R.layout.currentlocationsamplelistview,R.id.currentLocationSampleTextView_id,cityName);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = adapter.getItem(position);

                if(!value.isEmpty()){

                    Intent intent = new Intent(districtList_For_BloodBankSearch.this,BloodBankActivity.class);

                    intent.putExtra("districtNameCatch",value);

                    startActivity(intent);

                    finish();
                }
            }
        });
    }
}
