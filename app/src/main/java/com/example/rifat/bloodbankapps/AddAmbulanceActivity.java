package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAmbulanceActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;

    private EditText ambulanceNameEditText,ambulancePhoneEditText;
    private TextView ambulanceDistrictTextView;
    private ImageView ambulanceDistrictSpinner;

    private Button submitButton;

    private String nameFromDistrictList="";
    private String numberFromDistrictList="";
    private String districtFromDistrictList="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ambulance);

        this.setTitle("Add Ambulance page");

        databaseReference=FirebaseDatabase.getInstance().getReference("AmbulanceTable");


        ambulanceNameEditText=(EditText) findViewById(R.id.alertDialogAmbulanceNameEditText_id);
        ambulancePhoneEditText=(EditText) findViewById(R.id.alertDialogAmbulancePhoneEditText_id);
        ambulanceDistrictTextView=(TextView) findViewById(R.id.alertDialogAmbulanceLocationTextView_id);
        ambulanceDistrictSpinner=(ImageView)findViewById(R.id.alertDialogAmbulanceSpinner_id);

        submitButton=(Button)findViewById(R.id.addAmbulanceSubmitButton_id);

        ambulanceDistrictSpinner.setOnClickListener(this);
        submitButton.setOnClickListener(this);


        try{

            nameFromDistrictList=getIntent().getExtras().getString("BBNameFromDistrictList");
            numberFromDistrictList=getIntent().getExtras().getString("BBNumberFromDistrictList");
            districtFromDistrictList=getIntent().getExtras().getString("BBDistrictFromDistrictList");

            ambulanceNameEditText.setText(nameFromDistrictList);
            ambulancePhoneEditText.setText(numberFromDistrictList);
            ambulanceDistrictTextView.setText(districtFromDistrictList);

        }catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alertDialogAmbulanceSpinner_id){
            Intent intent =new Intent(AddAmbulanceActivity.this,districtListAlertDialogAmbulance.class);

            String takeBBName =  ambulanceNameEditText.getText().toString();
            String takeBBNumber =  ambulancePhoneEditText.getText().toString();

            intent.putExtra("bbName",takeBBName);
            intent.putExtra("bbNumber",takeBBNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.addAmbulanceSubmitButton_id){

            String t_bankName=ambulanceNameEditText.getText().toString();
            String t_bankNumber=ambulancePhoneEditText.getText().toString();
            String t_bankDistrict=ambulanceDistrictTextView.getText().toString();

            AmbulanceClass ambulanceClass = new AmbulanceClass(t_bankName,t_bankNumber,t_bankDistrict);
            databaseReference.child(t_bankDistrict).push().setValue(ambulanceClass);

            Toast.makeText(getApplicationContext(),"Ambulance Add Successfull !",Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(AddAmbulanceActivity.this,MainActivity.class);
            startActivity(intent);*/

            //finish();
        }
    }
}
