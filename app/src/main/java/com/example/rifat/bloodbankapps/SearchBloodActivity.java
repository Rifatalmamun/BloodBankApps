package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class SearchBloodActivity extends AppCompatActivity implements View.OnClickListener {


    // declaring all variables......................................................................
    private Spinner selectBloodGroup;
    private String[] bloodGroupArray;
    private AutoCompleteTextView districtName;
    private String[] districtArray;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

        this.setTitle("Search Blood Page");

        // find all variables......................................................

        selectBloodGroup=(Spinner)findViewById(R.id.searchPageSelectBloodGroup_id);
        districtName=(AutoCompleteTextView)findViewById(R.id.searchPageDistrictAutocompleteView_id);
        searchButton=(Button)findViewById(R.id.searchPageSearchButton_id);

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.searchpagebloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        selectBloodGroup.setAdapter(adapter);

        districtArray=getResources().getStringArray(R.array.DistrictArray);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districtArray);
        districtName.setThreshold(1);
        districtName.setAdapter(adapter1);

        // set onclick listener .................................
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.searchPageSearchButton_id){

            // take all item from searchBlood page
            String searchBloodGroup=selectBloodGroup.getSelectedItem().toString();
            String searchDistrict=districtName.getText().toString();

            // now check validation for all item..............................

            if(searchDistrict.isEmpty()){
                districtName.setError("please enter district name!");
                districtName.requestFocus();
                return;
            }

            if(!searchDistrict.isEmpty()){

                int check=checkDistrictName(searchDistrict);

                if(check==0){
                    districtName.setError("District Spelling error!!!");
                    districtName.requestFocus();
                    return;
                }
            }

            // now search all donor which blood group and district is given......................

            Intent intent = new Intent(SearchBloodActivity.this,SearchBloodResultShowActivity.class);
            // next intent a jabar somoy dui ta value niye jete hobe...........

            intent.putExtra("SendBloodGroup",searchBloodGroup);
            intent.putExtra("SendDistrictName",searchDistrict);

            startActivity(intent);

        }
    }
    // check District Name validation method......................................................
    private int checkDistrictName(String donorDistrict) {

        int flag=0;
        int i=0;
        for(i=0;i<districtArray.length;i++){

            String temp=districtArray[i];
            int checkValue=donorDistrict.compareToIgnoreCase(temp);

            if(checkValue==0){
                flag=1;
                break;
            }
        }

        return flag;
    }
}
