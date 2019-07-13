package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class BeDonor2Activity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference,databaseReference1;

    Random r;
    public int min,max,output;

    private EditText email,password,passwordRe;
    private Button beDonor2SubmitButton;


    private String rn,name,bloodGroup,district,phone,department,session,lastDonationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_donor2);
        this.setTitle("Registration");

        databaseReference=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");
        databaseReference1=FirebaseDatabase.getInstance().getReference("MyProfileTable");

        try{
            rn=getIntent().getExtras().getString("rn");
            name=getIntent().getExtras().getString("name");
            bloodGroup=getIntent().getExtras().getString("bloodGroup");
            district=getIntent().getExtras().getString("district");
            phone=getIntent().getExtras().getString("phone");
            department=getIntent().getExtras().getString("department");
            session=getIntent().getExtras().getString("session");
            lastDonationDate=getIntent().getExtras().getString("lastDonationDate");

        }catch(Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }


        //finding all id....................

        email=(EditText)findViewById(R.id.emailEditText_id);
        password=(EditText)findViewById(R.id.passwordEditText_id);
        passwordRe=(EditText)findViewById(R.id.passwordReEditText_id);
        beDonor2SubmitButton=(Button)findViewById(R.id.beDonor2SubmitButton);

        beDonor2SubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.beDonor2SubmitButton){

            donorStoreInDatabase();

        }
    }
    private void donorStoreInDatabase() {

        String donorPassword=password.getText().toString();
        String donorPasswordRe=passwordRe.getText().toString();
        String donorEmail=email.getText().toString();


        // Toast.makeText(this, "......"+donorDistrict, Toast.LENGTH_SHORT).show();
        //............validation all field.......................

        // internet connection check;
        if(!isConnected(BeDonor2Activity.this)){
            buildDialog(BeDonor2Activity.this).show();
        }else{
            String key=databaseReference.push().getKey();

            DonorClass donorClass = new DonorClass(rn,key,name,bloodGroup,phone,district,department,session,lastDonationDate);
            //DonorIdClass donorIdClass=new DonorIdClass(rn,key,donorPhoneNumber);
            DonorClass profileClass = new DonorClass(rn,key,name,bloodGroup,phone,district,department,session,lastDonationDate);

            databaseReference.child(bloodGroup).child(district).child(key).setValue(donorClass);
            //databaseReference1.child(donorBloodGroup).child(donorDistrict).child(key).setValue(donorIdClass);
            databaseReference1.child(phone).child(key).setValue(profileClass);

            Toast.makeText(getApplicationContext(),"Donor Add Successfull !",Toast.LENGTH_SHORT).show();

            loginInformationSaveInSharedPreference(phone);

            Intent intent = new Intent(BeDonor2Activity.this,MainActivity.class);
            intent.putExtra("checkPoint","finish");
            //intent.putExtra("phonePass",phone);
            startActivity(intent);
            finish();
        }
    }

    public void loginInformationSaveInSharedPreference(String phone) {
        AddDonorActivity.appsUserMobileNumber=phone;

        SharedPreferences  sharedPreferences=getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userLoginPhoneNumber",phone);

        editor.commit();
        Toast.makeText(BeDonor2Activity.this,"login info stored Successfully ",Toast.LENGTH_SHORT).show();
    }


    // clear all field value......................................................

 /*   private void clearAllFieldValue() {


        //email.setText("");
        phoneNumber.setText("");
        select_city.setText("");
        department.setText("");
        session.setText("");
    }*/


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
