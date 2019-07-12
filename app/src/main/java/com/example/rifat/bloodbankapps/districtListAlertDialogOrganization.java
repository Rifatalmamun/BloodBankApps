package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class districtListAlertDialogOrganization extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private String[] cityName;

    public String cN,cNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list_alert_dialog_organization);

        try{
            cN=getIntent().getExtras().getString("bbName");
            cNum=getIntent().getExtras().getString("bbNumber");

        }catch(Exception e){

        }


        searchView=(SearchView)findViewById(R.id.searchView_id);

        listView=(ListView)findViewById(R.id.currentDistrictListView_id);
        cityName=getResources().getStringArray(R.array.DistrictArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(districtListAlertDialogOrganization.this,R.layout.currentlocationsamplelistview,R.id.currentLocationSampleTextView_id,cityName);
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

                    Intent intent = new Intent(districtListAlertDialogOrganization.this,AddOrganizatoinActivity.class);

                    intent.putExtra("BBNameFromDistrictList",cN);
                    intent.putExtra("BBNumberFromDistrictList",cNum);
                    intent.putExtra("BBDistrictFromDistrictList",value);

                    startActivity(intent);
                    //Toast.makeText(districtListAlertDialogBloodBank.this, "city: "+value, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
