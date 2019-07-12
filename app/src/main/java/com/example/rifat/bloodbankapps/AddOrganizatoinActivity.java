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

public class AddOrganizatoinActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;

    private EditText organizationNameEditText,organizationPhoneEditText;
    private TextView organizationDistrictTextView;
    private ImageView organizationDistrictSpinner;

    private Button submitButton;

    private String nameFromDistrictList="";
    private String numberFromDistrictList="";
    private String districtFromDistrictList="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_organizatoin);

        this.setTitle("Add Organization page");

        databaseReference=FirebaseDatabase.getInstance().getReference("OrganizationTable");


        organizationNameEditText=(EditText) findViewById(R.id.alertDialogOrganizationNameEditText_id);
        organizationPhoneEditText=(EditText) findViewById(R.id.alertDialogOrganizationPhoneEditText_id);
        organizationDistrictTextView=(TextView) findViewById(R.id.alertDialogOrganizationLocationTextView_id);
        organizationDistrictSpinner=(ImageView)findViewById(R.id.alertDialogOrganizationSpinner_id);

        submitButton=(Button)findViewById(R.id.addOrganizationSubmitButton_id);

        organizationDistrictSpinner.setOnClickListener(this);
        submitButton.setOnClickListener(this);


        try{

            nameFromDistrictList=getIntent().getExtras().getString("BBNameFromDistrictList");
            numberFromDistrictList=getIntent().getExtras().getString("BBNumberFromDistrictList");
            districtFromDistrictList=getIntent().getExtras().getString("BBDistrictFromDistrictList");

            organizationNameEditText.setText(nameFromDistrictList);
            organizationPhoneEditText.setText(numberFromDistrictList);
            organizationDistrictTextView.setText(districtFromDistrictList);

        }catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alertDialogOrganizationSpinner_id){
            Intent intent =new Intent(AddOrganizatoinActivity.this,districtListAlertDialogOrganization.class);

            String takeBBName =  organizationNameEditText.getText().toString();
            String takeBBNumber =  organizationPhoneEditText.getText().toString();

            intent.putExtra("bbName",takeBBName);
            intent.putExtra("bbNumber",takeBBNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.addOrganizationSubmitButton_id){

            String t_bankName=organizationNameEditText.getText().toString();
            String t_bankNumber=organizationPhoneEditText.getText().toString();
            String t_bankDistrict=organizationDistrictTextView.getText().toString();

            OrganizationClass organizationClass = new OrganizationClass(t_bankName,t_bankNumber,t_bankDistrict);
            databaseReference.child(t_bankDistrict).push().setValue(organizationClass);

            Toast.makeText(getApplicationContext(),"Organization Add Successfull !",Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(AddBloodBankActivity.this,MainActivity.class);
            startActivity(intent);*/

            //finish();
        }
    }
}
