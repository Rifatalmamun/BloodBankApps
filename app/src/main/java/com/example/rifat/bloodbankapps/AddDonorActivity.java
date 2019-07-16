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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddDonorActivity extends AppCompatActivity  implements View.OnClickListener {

    ProgressDialog TempDialog;
    CountDownTimer CDT;
    int i =5;

    private FloatingActionButton floatingActionButton;
    private TextView loginPhnNumber,loginPassword;
    private Button loginButton;

    private ListView listViewProfile;
    private List<DonorClass> donorProfileList;
    //private ProfileCustomAdapter profileCustomAdapter,pf;
    private DonorClass donorClass;
    private String phn="",passw="";

    private String getName,getNumber,getDonationDate,getDepartmetn,getSession,getKey,getrn,getPassword;
    public String getBloodGroup,getDistrict;
    private LinearLayout loginInputPart,showpart,editpart,imgPart;

    private EditText updateNameEditText,updateBloodGrouopEditText,updateDistrictEditText,updatePhoneEditText,updateDepartmentEditText,updateSessionEditText,updateDonationDateEditText;
    private Button cancleButton,updateButton;
    private ProgressBar progressbarInAddDonor;

    private TextView myAcName,myAcBloodGroup,myAcLocation,myAcLastDate;

    private String acN,acBg,acDis,acDD,acPass;

    public static String appsUserMobileNumber="";
    public static String appsUserPasswordNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        imgPart=(LinearLayout)findViewById(R.id.imgSection_id);
        loginInputPart=(LinearLayout)findViewById(R.id.loginInputLinearlayout_id);

        loginPhnNumber=(TextView)findViewById(R.id.loginPhoneNumber_id);
        loginPassword=(TextView)findViewById(R.id.loginPassword_id);

        myAcName=(TextView)findViewById(R.id.myAccoutnName_id);
        myAcBloodGroup=(TextView)findViewById(R.id.myAccoutnBloodGroup_id);
        myAcLocation=(TextView)findViewById(R.id.myAccoutnLocation_id);
        myAcLastDate=(TextView)findViewById(R.id.myAccoutnLastDonatDate_id);

        //.............. Floating action Button..............................

        FloatingActionButton fab = findViewById(R.id.fab_id);
        fab.setOnClickListener(this);
        fab.setVerticalScrollbarPosition(0);

        //.............. Floating action Button...............................

        progressbarInAddDonor=(ProgressBar)findViewById(R.id.progressbarAddDonor_id);
        loginPhnNumber.setText(appsUserMobileNumber);
        loginPassword.setText(appsUserPasswordNumber);
        donorProfileList = new ArrayList<>();

        //if(!loginPhnNumber.equals("")){
        // String loginP=loginPhnNumber.getText().toString();
        loadUserProfileDataForFirstPage(appsUserMobileNumber);
        // }


    }

    private void loadUserProfileDataForFirstPage(String loginP) {

        final DatabaseReference profileRefForFirstPage = FirebaseDatabase.getInstance().getReference("MyProfileTable");

        profileRefForFirstPage.child(loginP).orderByChild("donor_phoneNumber")
                .equalTo(loginP).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorProfileList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);

                    acN = donorClass.getDonor_name();
                    acBg = donorClass.getDonor_bloodGroup();
                    acDis = donorClass.getDonor_district();
                    acDD = donorClass.getDonor_lastDonationDate();
                    acPass = donorClass.getDonor_password();

                    myAcName.setText("Name: "+acN);
                    myAcBloodGroup.setText("Blood Group: "+acBg);
                    myAcLocation.setText("Location: "+acDis);
                    myAcLastDate.setText("Last Donation Date: "+acDD);

                    progressbarInAddDonor.setVisibility(View.GONE);

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void read()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1=getSharedPreferences("UserPass",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("userPhnNumber")){
            String userPhn = sharedPreferences.getString("userPhnNumber","Not found");
            loginPhnNumber.setText(userPhn);

            //Toast.makeText(this, "show phone number", Toast.LENGTH_SHORT).show();
        }
        if(sharedPreferences1.contains("userPassNumber")){
            String userPass = sharedPreferences1.getString("userPassNumber","Not found");
            loginPassword.setText(userPass);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab_id){


            if(!isConnected(AddDonorActivity.this)){
                buildDialog(AddDonorActivity.this,"No Internet","Please Connect with Internet").show();
            }else{

                phn = loginPhnNumber.getText().toString();
                passw = loginPassword.getText().toString();

                if(phn.isEmpty()){
                    loginPhnNumber.setError("Please enter phone no!");
                    loginPhnNumber.requestFocus();
                    return;
                }
                if(passw.isEmpty()){
                    loginPassword.setError("Please enter password!");
                    loginPassword.requestFocus();
                    return;
                }
                if(passw.length()<6){
                    loginPassword.setError("Password length at lest 6!");
                    loginPassword.requestFocus();
                    return;
                }

                if(passw.equals(acPass)){

                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("userPhnNumber",phn);
                    editor.commit();
                    // Toast.makeText(this, "save phone number", Toast.LENGTH_SHORT).show();


                    // for password sharedPreferences.......................
                    SharedPreferences sharedPreferences1 = getSharedPreferences("UserPass",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                    editor1.putString("userPassNumber",passw);
                    editor1.commit();

                    loadUserProfileData(phn);



                   /* Intent intent = new Intent(AddDonorActivity.this,ProfileEditActivity.class);
                    startActivity(intent);*/
                }else{
                    Toast.makeText(this, "Password not match for show profile !!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void loadUserProfileData(String phn)
    {
        final int check = 0;
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
                    getPassword = donorClass.getDonor_password();



                    // donorProfileList.add(donorClass);
                }
                if(getBloodGroup==null){
                    buildDialog(AddDonorActivity.this,"Login Failed","Phone number or password don't match!").show();

                }if(loginPassword.getText().toString().equals(getPassword)){
                    //Toast.makeText(AddDonorActivity.this, ""+getName+"\n"+getBloodGroup+"\n"+getDistrict, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDonorActivity.this,ProfileEditActivity.class);
                    intent.putExtra("catchDonorName",getName);
                    intent.putExtra("catchDonorBloodGroup",getBloodGroup);
                    intent.putExtra("catchDonorDistrict",getDistrict);
                    intent.putExtra("catchDonorNumber",getNumber);
                    intent.putExtra("catchDonorDepartment",getDepartmetn);
                    intent.putExtra("catchDonorSession",getSession);
                    intent.putExtra("catchDonorDonationDate",getDonationDate);
                    intent.putExtra("catchDonorKey",getKey);
                    intent.putExtra("catchRandomNumber",getrn);
                    intent.putExtra("catchDonorPassword",getPassword);

                    ProfileEditActivity.flag=0;

                    startActivity(intent);
                    //finish();
                }else{
                    Toast.makeText(AddDonorActivity.this, "Password not match for updating", Toast.LENGTH_SHORT).show();
                }



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
    public AlertDialog.Builder buildDialog(Context c,String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);

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