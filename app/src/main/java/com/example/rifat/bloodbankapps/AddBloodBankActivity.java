package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBloodBankActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;

    private EditText bloodBankNameEditText,bloodBankPhoneEditText;
    private TextView bloodBankDistrictTextView;
    private ImageView bloodBankDistrictSpinner;

    private Button subminButton;

    private String nameFromDistrictList="";
    private String numberFromDistrictList="";
    private String districtFromDistrictList="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_bank);
        this.setTitle("Add Blood Bank page");

        databaseReference=FirebaseDatabase.getInstance().getReference("BloodBankTable");




        bloodBankNameEditText=(EditText) findViewById(R.id.alertDialogBloodBankNameEditText_id);
        bloodBankPhoneEditText=(EditText) findViewById(R.id.alertDialogBloodBankPhoneEditText_id);
        bloodBankDistrictTextView=(TextView) findViewById(R.id.alertDialogBloodBankLocationTextView_id);
        bloodBankDistrictSpinner=(ImageView)findViewById(R.id.alertDialogBloodBankSpinner_id);

        subminButton=(Button)findViewById(R.id.addBloodBankSubmitButton_id);

        bloodBankDistrictSpinner.setOnClickListener(this);
        subminButton.setOnClickListener(this);

        try{

            nameFromDistrictList=getIntent().getExtras().getString("BBNameFromDistrictList");
            numberFromDistrictList=getIntent().getExtras().getString("BBNumberFromDistrictList");
            districtFromDistrictList=getIntent().getExtras().getString("BBDistrictFromDistrictList");

            bloodBankNameEditText.setText(nameFromDistrictList);
            bloodBankPhoneEditText.setText(numberFromDistrictList);
            bloodBankDistrictTextView.setText(districtFromDistrictList);

        }catch (Exception e){

        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alertDialogBloodBankSpinner_id){
            Intent intent =new Intent(AddBloodBankActivity.this,districtListAlertDialogBloodBank.class);

            String takeBBName =  bloodBankNameEditText.getText().toString();
            String takeBBNumber =  bloodBankPhoneEditText.getText().toString();

            intent.putExtra("bbName",takeBBName);
            intent.putExtra("bbNumber",takeBBNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.addBloodBankSubmitButton_id){

            String t_bankName=bloodBankNameEditText.getText().toString();
            String t_bankNumber=bloodBankPhoneEditText.getText().toString();
            String t_bankDistrict=bloodBankDistrictTextView.getText().toString();

            BloodBankClass bloodBankClass = new BloodBankClass(t_bankName,t_bankNumber,t_bankDistrict);
            databaseReference.child(t_bankDistrict).push().setValue(bloodBankClass);

            Toast.makeText(getApplicationContext(),"Blood Bank Add Successfull !",Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(AddBloodBankActivity.this,MainActivity.class);
            startActivity(intent);*/

            //finish();
        }
    }


}
