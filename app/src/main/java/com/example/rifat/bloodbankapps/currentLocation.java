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

public class currentLocation extends AppCompatActivity  {

    private SearchView searchView;

    private ListView listView;
    private String[] cityName;
    private String pos;

    public String catchName,catchBloodGroup;
    public String cN,cB,cD,cP,cDept,cS,cDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        try{
            cN=getIntent().getExtras().getString("_name");
            cB=getIntent().getExtras().getString("_bloodgroup");
            cD=getIntent().getExtras().getString("_district");
            cP=getIntent().getExtras().getString("_phone");
            cDept=getIntent().getExtras().getString("_department");
            cS=getIntent().getExtras().getString("_session");
            cDate=getIntent().getExtras().getString("_donationdate");

            /*catchName=getIntent().getExtras().getString("sendName");
            catchBloodGroup=getIntent().getExtras().getString("sendBloodGroup");*/

            if(cB.equals("A+")){
                pos = "0";
            }else if(cB.equals("O+")){
                pos = "1";
            }else if(cB.equals("AB+")){
                pos = "2";
            }else if(cB.equals("B+")){
                pos = "3";
            }else if(cB.equals("A-")){
                pos = "4";
            }else if(cB.equals("B-")){
                pos = "5";
            }else if(cB.equals("AB-")){
                pos = "6";
            }else if(cB.equals("O-")){
                pos = "7";
            }
        }catch(Exception e){

        }



        searchView=(SearchView)findViewById(R.id.searchView_id);


        listView=(ListView)findViewById(R.id.currentLocationListView_id);
        cityName=getResources().getStringArray(R.array.DistrictArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(currentLocation.this,R.layout.currentlocationsamplelistview,R.id.currentLocationSampleTextView_id,cityName);
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

                    Intent intent = new Intent(currentLocation.this,BeDonorActivity.class);

                    /*intent.putExtra("selectedCity",value);
                    intent.putExtra("DN",catchName);
                    intent.putExtra("BG",catchBloodGroup);
                    intent.putExtra("P",pos);*/


                    intent.putExtra("Name",cN);
                    intent.putExtra("Position",pos);
                    intent.putExtra("District",value);
                    intent.putExtra("PhoneNumber",cP);
                    intent.putExtra("Department",cDept);
                    intent.putExtra("Session",cS);
                    intent.putExtra("DonationDate",cDate);


                    startActivity(intent);
                    Toast.makeText(currentLocation.this, "city: "+value, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}