package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    ProgressDialog TempDialog;
    CountDownTimer CDT;
    int i =5;

    private FloatingActionButton floatingActionButton;
    private EditText loginPhnNumber,loginPassword;
    private Button loginButton;

    private ListView listViewProfile;
    private List<DonorClass> donorProfileList;
    //private ProfileCustomAdapter profileCustomAdapter,pf;
    private DonorClass donorClass;
    private String phn="",passw="";

    private String getName,getNumber,getBloodGroup,getDistrict,getDonationDate,getDepartmetn,getSession,getKey,getrn;
    private LinearLayout loginInputPart,showpart,editpart,imgPart;

    private EditText updateNameEditText,updateBloodGrouopEditText,updateDistrictEditText,updatePhoneEditText,updateDepartmentEditText,updateSessionEditText,updateDonationDateEditText;
    private Button cancleButton,updateButton;

    private ProgressBar progressbarInAddDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);


        //..................................................................




        //////////////////////////////////////////////////////

        //floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);

        imgPart=(LinearLayout)findViewById(R.id.imgSection_id);
        loginInputPart=(LinearLayout)findViewById(R.id.loginInputLinearlayout_id);
        //showpart=(LinearLayout)findViewById(R.id.showPart_id);
        //editpart=(LinearLayout)findViewById(R.id.editPart_id);
        //editpart.setVisibility(View.GONE);

        /*floatingActionButton.hide();

        //.............. Floating action Button..............................

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*

                if(!isConnected(AddDonorActivity.this)){
                    buildDialog(AddDonorActivity.this).show();
                }
                else{

                    if(updateNameEditText.getText().toString().isEmpty()){

                        editpart.setVisibility(view.GONE);
                        showpart.setVisibility(View.VISIBLE);
                        loginInputPart.setVisibility(View.VISIBLE);
                    }else{
                        editpart.setVisibility(View.VISIBLE);
                        showpart.setVisibility(View.GONE);
                        loginInputPart.setVisibility(View.GONE);
                    }
                }


            }
        });
*/
        //.............. Floating action Button...............................

        loginPhnNumber=(EditText)findViewById(R.id.loginPhoneNumber_id);
        loginPassword=(EditText)findViewById(R.id.loginPassword_id);


        loginButton=(Button)findViewById(R.id.loginButton_id);

        loginButton.setOnClickListener(this);

        progressbarInAddDonor=(ProgressBar)findViewById(R.id.progressbarAddDonor_id);

        read();

        //listViewProfile = (ListView)findViewById(R.id.profileListView_id);
        donorProfileList = new ArrayList<>();
       // profileCustomAdapter = new ProfileCustomAdapter(AddDonorActivity.this,donorProfileList);


       /* updateNameEditText=(EditText)findViewById(R.id.updateNameEditText_id);
        updateBloodGrouopEditText=(EditText)findViewById(R.id.updateBloodGroupEditText_id);
        updateDistrictEditText=(EditText)findViewById(R.id.updateDistrictEditText_id);
        updatePhoneEditText=(EditText)findViewById(R.id.updatePhoneEditText_id);
        updateDepartmentEditText=(EditText)findViewById(R.id.updateDepartmentEditText_id);
        updateSessionEditText=(EditText)findViewById(R.id.updateSessionEditText_id);
        updateDonationDateEditText=(EditText)findViewById(R.id.updateDonationDateEditText_id);

        cancleButton=(Button)findViewById(R.id.cancleButton_id);
        updateButton=(Button)findViewById(R.id.updateButton_id);

        cancleButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);*/
    }

    private void read()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1=getSharedPreferences("UserPass",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("userPhnNumber")){
            String userPhn = sharedPreferences.getString("userPhnNumber","Not found");
            loginPhnNumber.setText(userPhn);

            Toast.makeText(this, "show phone number", Toast.LENGTH_SHORT).show();
        }
        if(sharedPreferences1.contains("userPassNumber")){
            String userPass = sharedPreferences1.getString("userPassNumber","Not found");
            loginPassword.setText(userPass);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginButton_id){

            /*loginInputPart.setVisibility(View.VISIBLE);
            editpart.setVisibility(View.GONE);
            showpart.setVisibility(View.VISIBLE);
*/
            if(!isConnected(AddDonorActivity.this)){
                buildDialog(AddDonorActivity.this).show();
            }else{

                phn = loginPhnNumber.getText().toString();
                passw = loginPassword.getText().toString();

                if(phn.isEmpty())
                    Toast.makeText(this, "Please enter phn no!", Toast.LENGTH_SHORT).show();
                if(passw.isEmpty())
                    Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
                else{
                    //progressbarInAddDonor.setVisibility(View.VISIBLE);
                    //listViewProfile.setVisibility(View.VISIBLE);
                    // for phone number sharedPreferences...................
                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("userPhnNumber",phn);
                    editor.commit();
                    Toast.makeText(this, "save phone number", Toast.LENGTH_SHORT).show();


                    // for password sharedPreferences.......................
                    SharedPreferences sharedPreferences1 = getSharedPreferences("UserPass",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                    editor1.putString("userPassNumber",passw);
                    editor1.commit();

                    loadUserProfileData(phn);
                    //tempDialog();
                   // progressbarInAddDonor.setVisibility(View.GONE);

                   // tempDialog();


                    Intent intent = new Intent(AddDonorActivity.this,ProfileEditActivity.class);
                    startActivity(intent);
                }
            }
        }
      /*  else if(v.getId()==R.id.cancleButton_id){
            editpart.setVisibility(View.GONE);
            loginInputPart.setVisibility(View.VISIBLE);
            showpart.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.updateButton_id){

            updateUserProfileDate(phn);

            loginInputPart.setVisibility(View.VISIBLE);
            editpart.setVisibility(View.GONE);
            showpart.setVisibility(View.VISIBLE);
        }*/

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
                    getDepartmetn = donorClass.getDonor_department();
                    getSession = donorClass.getDonor_session();
                    getKey = donorClass.getDonor_key();
                    getrn = donorClass.getDonor_rn();
                }

                Toast.makeText(AddDonorActivity.this, ""+getName+"\n"+getBloodGroup+"\n"+getDistrict, Toast.LENGTH_SHORT).show();

                //listViewProfile.setAdapter(profileCustomAdapter);


                // Setting information
                /*updateNameEditText.setText(getName);
                updateBloodGrouopEditText.setText(getBloodGroup);
                updateDistrictEditText.setText(getDistrict);
                updatePhoneEditText.setText(getNumber);
                updateDonationDateEditText.setText(getDonationDate);
                if(getDepartmetn.isEmpty()){
                    updateDepartmentEditText.setVisibility(View.GONE);
                }else{
                    updateDepartmentEditText.setText(getDepartmetn);
                }

                if(getSession.isEmpty()){
                    updateSessionEditText.setVisibility(View.GONE);
                }else{
                    updateSessionEditText.setText(getSession);
                }

                Toast.makeText(AddDonorActivity.this, "un: "+getName, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //imgPart.setVisibility(View.GONE);
        //floatingActionButton.show();

    }
//...............data update method.....................................

    private void updateUserProfileDate(String phn)
    {
        /*String un = updateNameEditText.getText().toString();
        String ubg = updateBloodGrouopEditText.getText().toString();
        String ud = updateDistrictEditText.getText().toString();
        String up = updatePhoneEditText.getText().toString();
        String udd = updateDonationDateEditText.getText().toString();*/

       // final DonorClass donorClass = new DonorClass(getrn,getKey,un,ubg,up,ud,getDepartmetn,getSession,udd);

        /*final DatabaseReference updateProfileRef = FirebaseDatabase.getInstance().getReference("MyProfileTable");
        //String key=databaseReference.push().getKey();

        updateProfileRef.child(getNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //dataSnapshot.getRef().child(getNumber).removeValue();

                String k =  dataSnapshot.getKey();

                DonorClass d = dataSnapshot.getValue(DonorClass.class);


                dataSnapshot.getRef().child(getNumber).child(k).setValue(d);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
        //DonorIdClass donorIdClass=new DonorIdClass(rn,key,donorPhoneNumber);
        //DonorClass profileClass = new DonorClass(rn,key,donorName,donorBloodGroup,donorPhoneNumber,donorDistrict,donorDepartment,donorSession,donorLastDonationDate);

        //updateProfileRef.child(getNumber).push().setValue(donorClass);
        //databaseReference1.child(donorBloodGroup).child(donorDistrict).push().setValue(donorIdClass);
        //databaseReference2.child(donorPhoneNumber).push().setValue(profileClass);

        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
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
    // temporary dialog ..............................................
     public void tempDialog()
    {
        //int i =5;

        TempDialog = new ProgressDialog(AddDonorActivity.this);
        TempDialog.setMessage("Please wait...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.show();

        CDT = new CountDownTimer(5000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                TempDialog.setMessage("Please wait...");
                i=i-1;
            }
            public void onFinish()
            {

                //Your Code ...
                Intent intent = new Intent(AddDonorActivity.this,ProfileEditActivity.class);

                //progressbarInAddDonor.setVisibility(View.GONE);
                startActivity(intent);

                TempDialog.dismiss();
            }
        }.start();

    }
}
