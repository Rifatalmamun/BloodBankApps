package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDonorActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText loginPhnNumber;
    private Button loginButton;

    private ListView listViewProfile;
    private List<DonorClass> donorProfileList;
    private ProfileCustomAdapter profileCustomAdapter,pf;
    private DonorClass donorClass;
    private String phn="";

    private String getName,getNumber,getBloodGroup,getDistrict,getDonationDate;
    private LinearLayout loginInputPart,showpart,editpart;

    private EditText updateNameEditText,updateBloodGrouopEditText,updateDistrictEditText,updatePhoneEditText,updateDonationDateEditText;
    private Button cancleButton,updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        loginInputPart=(LinearLayout)findViewById(R.id.loginInputLinearlayout_id);
        showpart=(LinearLayout)findViewById(R.id.showPart_id);
        editpart=(LinearLayout)findViewById(R.id.editPart_id);
        editpart.setVisibility(View.GONE);

        //.............. Floating action Button..............................

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if(!isConnected(AddDonorActivity.this)){
                    buildDialog(AddDonorActivity.this).show();
                }
                else{
                    //pf = new ProfileCustomAdapter(AddDonorActivity.this,donorProfileList);

                     //String n = donorClass.getDonor_name();

                   // Toast.makeText(AddDonorActivity.this, n, Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(AddDonorActivity.this,ProfileEditActivity.class);



                    //Toast.makeText(AddDonorActivity.this, "Name: "+N+"\nPhone"+P, Toast.LENGTH_SHORT).show();

                   // startActivity(intent);

                    editpart.setVisibility(View.VISIBLE);
                    showpart.setVisibility(View.GONE);
                    loginInputPart.setVisibility(View.GONE);
                }


            }
        });



        //.............. Floating action Button...............................

        loginPhnNumber=(EditText)findViewById(R.id.loginPhoneNumber_id);
        loginButton=(Button)findViewById(R.id.loginButton_id);

        loginButton.setOnClickListener(this);

        read();

        listViewProfile = (ListView)findViewById(R.id.profileListView_id);
        donorProfileList = new ArrayList<>();
        profileCustomAdapter = new ProfileCustomAdapter(AddDonorActivity.this,donorProfileList);


        updateNameEditText=(EditText)findViewById(R.id.updateNameEditText_id);
        updateBloodGrouopEditText=(EditText)findViewById(R.id.updateBloodGroupEditText_id);
        updateDistrictEditText=(EditText)findViewById(R.id.updateDistrictEditText_id);
        updatePhoneEditText=(EditText)findViewById(R.id.updatePhoneEditText_id);
        updateDonationDateEditText=(EditText)findViewById(R.id.updateDonationDateEditText_id);

        cancleButton=(Button)findViewById(R.id.cancleButton_id);
        updateButton=(Button)findViewById(R.id.updateButton_id);

        cancleButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    private void read()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("userPhnNumber")){
            String userPhn = sharedPreferences.getString("userPhnNumber","Not found");
            loginPhnNumber.setText(userPhn);

            Toast.makeText(this, "show phone number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginButton_id){

            loginInputPart.setVisibility(View.VISIBLE);
            editpart.setVisibility(View.GONE);
            showpart.setVisibility(View.VISIBLE);

            if(!isConnected(AddDonorActivity.this)){
                buildDialog(AddDonorActivity.this).show();
            }else{

                phn = loginPhnNumber.getText().toString();

                if(phn.isEmpty())
                    Toast.makeText(this, "Please enter phn no", Toast.LENGTH_SHORT).show();
                else{

                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("userPhnNumber",phn);
                    editor.commit();
                    Toast.makeText(this, "save phone number", Toast.LENGTH_SHORT).show();

                    loadUserProfileData(phn);

                }

            }


        }
        else if(v.getId()==R.id.cancleButton_id){
            editpart.setVisibility(View.GONE);
            loginInputPart.setVisibility(View.VISIBLE);
            showpart.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.updateButton_id){

            updateUserProfileDate(phn);

            loginInputPart.setVisibility(View.VISIBLE);
            editpart.setVisibility(View.GONE);
            showpart.setVisibility(View.VISIBLE);
        }

    }



    private void loadUserProfileData(String phn)
    {
        //......................database access...............................

        final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("MyProfileTable");

        profileRef.child(phn).orderByChild("donor_phoneNumber")
                .equalTo(phn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorProfileList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);
                    donorProfileList.add(donorClass);

                    // getting information......................
                    getName = donorClass.getDonor_name();
                    getBloodGroup = donorClass.getDonor_bloodGroup();
                    getDistrict = donorClass.getDonor_district();
                    getNumber = donorClass.getDonor_phoneNumber();
                    getDonationDate = donorClass.getDonor_lastDonationDate();
                    // Setting information
                    updateNameEditText.setText(getName);
                    updateBloodGrouopEditText.setText(getBloodGroup);
                    updateDistrictEditText.setText(getDistrict);
                    updatePhoneEditText.setText(getNumber);
                    updateDonationDateEditText.setText(getDonationDate);
                }
                listViewProfile.setAdapter(profileCustomAdapter);

                //String name =donorClass.getDonor_name() ;
                //String number = donorClass.getDonor_phoneNumber();

                Toast.makeText(AddDonorActivity.this, getName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//...............data update method.....................................

    private void updateUserProfileDate(String phn)
    {
        final DatabaseReference updateProfileRef = FirebaseDatabase.getInstance().getReference("MyProfileTable");



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
