package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EmmergencyPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner bloodGroupSpinner;
    private ImageView districtImageView;
    private TextView districtTextView;
    private EditText hospitalEditText,phoneEditText,detailstEditText;
    private Button sendPostButton;

    private String[] bloodGroupArray;

    private String catchName="";
    private String catchPositionString="";
    private int catchPosition;
    private String catchDistrict="";
    private String catchPhoneNumber="";
    private String catchDetails="";


    private String takeHospitalName="";
    private String takePosition="";
    private String takeDistrict="";
    private String takePhoneNumber="";
    private String takeDetails="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emmergency_post);
        this.setTitle("Emmergency Post");


        // first find all the item.............
        bloodGroupSpinner=(Spinner)findViewById(R.id.postSelectBloodGroupSpinner_id);
        districtImageView=(ImageView)findViewById(R.id.postSelectCitySpinner_id);
        districtTextView=(TextView)findViewById(R.id.postSelectCityTextView_id);
        hospitalEditText=(EditText)findViewById(R.id.postHospitalNameEditText_id);
        phoneEditText=(EditText)findViewById(R.id.postPhoneNumberEditText_id);
        detailstEditText=(EditText)findViewById(R.id.postDetailsEditText_id);
        sendPostButton=(Button)findViewById(R.id.postSubmitButton_id);

        // blood group spinner and this word done..............................................

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.searchpagebloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        bloodGroupSpinner.setAdapter(adapter);

        districtImageView.setOnClickListener(this);
        sendPostButton.setOnClickListener(this);

        try {
            catchName=getIntent().getExtras().getString("HN");
            catchPositionString=getIntent().getExtras().getString("P");
            catchPosition=Integer.parseInt(catchPositionString);
            catchDistrict=getIntent().getExtras().getString("selectedCity");
            catchPhoneNumber=getIntent().getExtras().getString("PN");
            catchDetails=getIntent().getExtras().getString("DET");




            hospitalEditText.setText(catchName);
            bloodGroupSpinner.setSelection(catchPosition);
            districtTextView.setText(catchDistrict);
            phoneEditText.setText(catchPhoneNumber);
            detailstEditText.setText(catchDetails);


            // Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.postSelectCitySpinner_id){
            Intent intent  = new Intent(EmmergencyPostActivity.this,districtList_For_EmmergencyPost.class);
            // take all fields value then go to postDistrictlist activity..................
            takeHospitalName=hospitalEditText.getText().toString();
            takePosition=bloodGroupSpinner.getSelectedItem().toString();
            takeDistrict=districtTextView.getText().toString();
            takePhoneNumber=phoneEditText.getText().toString();
            takeDetails=detailstEditText.getText().toString();


            intent.putExtra("sendName",takeHospitalName);
            intent.putExtra("sendBloodGroup",takePosition);
            intent.putExtra("sendPhone",takePhoneNumber);
            intent.putExtra("sendDetails",takeDetails);


            startActivity(intent);
            finish();

        }if(v.getId()==R.id.postSubmitButton_id){
            String post_hospital=hospitalEditText.getText().toString();
            String post_blood=bloodGroupSpinner.getSelectedItem().toString();
            String post_district=districtTextView.getText().toString();
            String post_phone=phoneEditText.getText().toString();
            String post_details=detailstEditText.getText().toString();

            if(post_hospital.isEmpty()){
                hospitalEditText.setError("enter hospital/location");
                hospitalEditText.requestFocus();
                return;
            }
            if(post_district.isEmpty()){
                districtTextView.setError("select district");
                districtTextView.requestFocus();
                return;
            }
            if(post_phone.isEmpty()){
                phoneEditText.setError("enter phone number");
                phoneEditText.requestFocus();
                return;
            }
            if(post_phone.length()>11 || post_phone.length()<11){
                phoneEditText.setError("number length must be 11");
                phoneEditText.requestFocus();
                return;
            }if(post_details.isEmpty()){
                detailstEditText.setError("please enter details");
                detailstEditText.requestFocus();
                return;
            }


            /*Toast.makeText(this, "BloodGroup: "+takePosition, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Hospital: "+takeHospitalName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "District: "+takeDistrict, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Phone: "+takePhoneNumber, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Details: "+takeDetails, Toast.LENGTH_SHORT).show();*/
        }
    }
}
