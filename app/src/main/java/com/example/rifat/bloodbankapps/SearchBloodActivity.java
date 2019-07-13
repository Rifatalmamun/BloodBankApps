package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchBloodActivity extends AppCompatActivity implements View.OnClickListener {


    // declaring all variables......................................................................
    private Spinner selectBloodGroup;
    private String[] bloodGroupArray;
    private TextView districtName;
    private ImageView imageView;
    private String[] districtArray;
    private Button searchButton;

    private String searchBloodGroup;
    private String searchDistrict;

    String catchSelectedCity="";
    String catchBG="";
    static int flage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

        this.setTitle("Search Blood");



        // find all variables......................................................

        selectBloodGroup=(Spinner)findViewById(R.id.searchPageSelectBloodGroup_id);
        districtName=(TextView)findViewById(R.id.selectCityTextView_id);
        searchButton=(Button)findViewById(R.id.searchPageSearchButton_id);

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.searchpagebloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        selectBloodGroup.setAdapter(adapter);

        /*districtArray=getResources().getStringArray(R.array.DistrictArray);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districtArray);
        districtName.setThreshold(1);
        districtName.setAdapter(adapter1);*/

        imageView=(ImageView)findViewById(R.id.selectCityImageView_id);

        // set onclick listener .................................
        searchButton.setOnClickListener(this);
        imageView.setOnClickListener(this);

        if(flage > 0){
            try {
                catchSelectedCity=getIntent().getExtras().getString("selectedCity");
                districtName.setText(catchSelectedCity);

                catchBG=getIntent().getExtras().getString("P");
                int posi = Integer.parseInt(catchBG);
                selectBloodGroup.setSelection(posi);

                // Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.selectCityImageView_id){

            Intent intent  = new Intent(SearchBloodActivity.this,currentlocation2.class);
            flage=1;

            searchBloodGroup = selectBloodGroup.getSelectedItem().toString();

            intent.putExtra("sendBloodGroup",searchBloodGroup);

            startActivity(intent);
            finish();
        }

        if(v.getId()==R.id.searchPageSearchButton_id){

            // take all item from searchBlood page
            searchBloodGroup=selectBloodGroup.getSelectedItem().toString();
            searchDistrict=districtName.getText().toString();

            // now check validation for all item..............................

            if(searchDistrict.isEmpty()){
                districtName.setError("empty!");
                districtName.requestFocus();
                Toast.makeText(getApplicationContext(), "Please select District...", Toast.LENGTH_SHORT).show();
                return;

            }

            /*if(!searchDistrict.isEmpty()){
                int check=checkDistrictName(searchDistrict);
                if(check==0){
                    districtName.setError("District Spelling error!!!");
                    districtName.requestFocus();
                    return;
                }
            }*/
            // internet connection check;
            if(!isConnected(SearchBloodActivity.this)){
                buildDialog(SearchBloodActivity.this).show();


            }else{
                // now search all donor which blood group and district is given......................

                Intent intent = new Intent(SearchBloodActivity.this,SearchBloodResultShowActivity.class);
                // next intent a jabar somoy dui ta value niye jete hobe...........

                intent.putExtra("SendBloodGroup",searchBloodGroup);
                intent.putExtra("SendDistrictName",searchDistrict);

                startActivity(intent);
            }
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

    //.................................... net conected check............................................

    public boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if(netinfo !=null && netinfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile !=null && mobile.isConnectedOrConnecting()) || (wifi !=null && wifi.isConnectedOrConnecting()) ){
                return true;
            }else{
                return false;
            }
        }else
            return false;
    }

    // alert dialog.....................
    public AlertDialog.Builder buildDialog(Context c)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection!");
        builder.setMessage("You need to connect your device with internet ........");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        return builder;
    }
}