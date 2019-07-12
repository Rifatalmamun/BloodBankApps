package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.Random;

public class BeDonorActivity extends AppCompatActivity implements View.OnClickListener {
    private Context ctx;
    //private DatabaseReference databaseReference,databaseReference1; // for firebase

    private EditText name,phoneNumber;
    private Spinner selectBloodGroup;
    private ImageView selectCitySpinner,departmentSpinner,sessionSpinner;

    private String[] bloodGroupArray;
    private String[] districtNameArray;
    private Button beDonorNextButton,beDonorCancleButton;
    private Button datePickerButton;
    private TextView lastDonationTextView,iDontKnowTextView,select_city,department,session;
    private TextView alreadyRegistered;
    private DatePickerDialog date_Picker_Dialog;

    private String donorName,donorBloodGroup,donorDistrict,donorPhoneNumber,donorEmail,donorDepartment,donorSession,donorLastDonationDate;

    Random r;
    public int min,max,output;
   /* String catchSelectedCity="";
    String catchDN="";
    String catchBG="";*/
    Info in;
    static int flage=0;

    private String catchName="";
    private String catchPositionString="";
    private int catchPosition;
    private String catchDistrict="";
    private String catchPhoneNumber="";
    private String catchDepartment="";
    private String catchSession="";
    private String catchDonationDate="";

    private String takeName="";
    private String takePosition="";
    private String takeDistrict="";
    private String takePhoneNumber="";
    private String takeDepartment="";
    private String takeSession="";
    private String takeDonationDate="";

    private int dateFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_donor);



        /*databaseReference=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");

        databaseReference1=FirebaseDatabase.getInstance().getReference("MyProfileTable");*/
        this.setTitle("Be a Donor page");

        r=new Random();
        // finding all variables....................................................................

        name=(EditText)findViewById(R.id.nameEditText_id);
        selectBloodGroup=(Spinner)findViewById(R.id.selectBloodGroup_id);
        selectCitySpinner=(ImageView) findViewById(R.id.selectCitySpinner_id);
        departmentSpinner=(ImageView)findViewById(R.id.departmentSpinner_id);
        phoneNumber=(EditText)findViewById(R.id.phoneNumberEditText_id);
        department=(TextView) findViewById(R.id.departmentTextView_id);
        sessionSpinner=(ImageView)findViewById(R.id.sessionSpinner_id);
        session=(TextView) findViewById(R.id.sessionTextView_id);
        beDonorNextButton=(Button)findViewById(R.id.beDonorNextButton_id);
        //beDonorCancleButton=(Button)findViewById(R.id.beDonorCancleButton_id);
        datePickerButton=(Button)findViewById(R.id.datePicker_id);
        iDontKnowTextView=(TextView)findViewById(R.id.iDontKnowTextView_id);
        lastDonationTextView=(TextView)findViewById(R.id.lastDonationDate_id);
        select_city=(TextView)findViewById(R.id.selectCity_id);
        select_city.setText(catchDistrict);

        alreadyRegistered=(TextView)findViewById(R.id.beDonorAlreadyRegisteredTextView_id);

        // Blood Group Spinner......................................................................

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.bloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        selectBloodGroup.setAdapter(adapter1);


        // set onclick listener...............................................................
        beDonorNextButton.setOnClickListener(this);
       // beDonorCancleButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);
        selectCitySpinner.setOnClickListener(this);
        departmentSpinner.setOnClickListener(this);
        sessionSpinner.setOnClickListener(this);
        //iDontKnowTextView.setOnClickListener(this);
        lastDonationTextView.setOnClickListener(this);
        alreadyRegistered.setOnClickListener(this);

       // if(flage > 0){
            try {
                catchName=getIntent().getExtras().getString("Name");
                catchPositionString=getIntent().getExtras().getString("Position");
                catchPosition=Integer.parseInt(catchPositionString);
                catchDistrict=getIntent().getExtras().getString("District");
                catchPhoneNumber=getIntent().getExtras().getString("PhoneNumber");
                catchDepartment=getIntent().getExtras().getString("Department");
                catchSession=getIntent().getExtras().getString("Session");
                catchDonationDate=getIntent().getExtras().getString("DonationDate");

                name.setText(catchName);
                selectBloodGroup.setSelection(catchPosition);
                select_city.setText(catchDistrict);
                phoneNumber.setText(catchPhoneNumber);
                department.setText(catchDepartment);
                session.setText(catchSession);
                iDontKnowTextView.setText(catchDonationDate);

                // Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }catch (Exception e){

            }
        //}


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.selectCitySpinner_id){

            // go to city name list page and select a city name..........
            Intent intent  = new Intent(BeDonorActivity.this,currentLocation.class);
            flage=1;


            takeName=name.getText().toString();
            takePosition=selectBloodGroup.getSelectedItem().toString();
            takeDistrict=select_city.getText().toString();
            takePhoneNumber=phoneNumber.getText().toString();
            takeDepartment=department.getText().toString();
            takeSession=session.getText().toString();
            takeDonationDate=iDontKnowTextView.getText().toString();

            intent.putExtra("_name",takeName);
            intent.putExtra("_bloodgroup",takePosition);
            intent.putExtra("_district",takeDistrict);
            intent.putExtra("_phone",takePhoneNumber);
            intent.putExtra("_department",takeDepartment);
            intent.putExtra("_session",takeSession);
            intent.putExtra("_donationdate",takeDonationDate);

            /*donorName = name.getText().toString();
            donorBloodGroup=selectBloodGroup.getSelectedItem().toString();*/

            /*intent.putExtra("sendName",takeName);
            intent.putExtra("sendBloodGroup",takePosition);*/
            //Toast.makeText(this, "N: "+donorName, Toast.LENGTH_SHORT).show();

            startActivity(intent);

            finish();
        }
        if(v.getId()==R.id.departmentSpinner_id){
            flage=1;
            Intent intent  = new Intent(BeDonorActivity.this,departmentlist_For_BEDONOR.class);

            takeName=name.getText().toString();
            takePosition=selectBloodGroup.getSelectedItem().toString();
            takeDistrict=select_city.getText().toString();
            takePhoneNumber=phoneNumber.getText().toString();
            takeDepartment=department.getText().toString();
            takeSession=session.getText().toString();
            takeDonationDate=iDontKnowTextView.getText().toString();

            intent.putExtra("_name",takeName);
            intent.putExtra("_bloodgroup",takePosition);
            intent.putExtra("_district",takeDistrict);
            intent.putExtra("_phone",takePhoneNumber);
            intent.putExtra("_department",takeDepartment);
            intent.putExtra("_session",takeSession);
            intent.putExtra("_donationdate",takeDonationDate);



            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.sessionSpinner_id){

            flage=1;
            Intent intent  = new Intent(BeDonorActivity.this,sessionlist_For_BEDONOR.class);

            takeName=name.getText().toString();
            takePosition=selectBloodGroup.getSelectedItem().toString();
            takeDistrict=select_city.getText().toString();
            takePhoneNumber=phoneNumber.getText().toString();
            takeDepartment=department.getText().toString();
            takeSession=session.getText().toString();
            takeDonationDate=iDontKnowTextView.getText().toString();

            intent.putExtra("_name",takeName);
            intent.putExtra("_bloodgroup",takePosition);
            intent.putExtra("_district",takeDistrict);
            intent.putExtra("_phone",takePhoneNumber);
            intent.putExtra("_department",takeDepartment);
            intent.putExtra("_session",takeSession);
            intent.putExtra("_donationdate",takeDonationDate);



            startActivity(intent);
            finish();



        }

        if(v.getId()==R.id.datePicker_id){
            openDatePicker();
            //Toast.makeText(getApplicationContext(), "clicked button", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.lastDonationDate_id){
            if(!lastDonationTextView.getText().toString().equals("Last Donation date")){
                lastDonationTextView.setText("Last Donation date");
                iDontKnowTextView.setText("I don't know");
            }

        }

        if(v.getId()==R.id.beDonorNextButton_id){

            // first create 10 digit random number;.................
            String random_number="";

            String tempMin="100",temMax="1000000000";

            if( !tempMin.equals("") && !temMax.equals("") )
            {
                min=Integer.parseInt(tempMin);
                max=Integer.parseInt(temMax);

                output=r.nextInt((max-min)+1)+min;

                random_number=Integer.toString(output);
            }

            //Toast.makeText(getApplicationContext(), "clicked!!"+random_number, Toast.LENGTH_SHORT).show();

            donorStoreInDatabase(random_number);
        }
        if(v.getId()==R.id.beDonorAlreadyRegisteredTextView_id){
            Intent intent =new Intent(BeDonorActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        /*if(v.getId()==R.id.beDonorCancleButton_id){
            Intent intent = new Intent(BeDonorActivity.this,BeDonor2Activity.class);
            beDonorCancleButton.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            startActivity(intent);
        }*/
    }

    private void openDatePicker() {

        DatePicker datePicker1=new DatePicker(this);
        int currentDay = datePicker1.getDayOfMonth();
        int currentMonth = (datePicker1.getMonth());
        int currentYear = datePicker1.getYear();

        date_Picker_Dialog=new DatePickerDialog(this,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dayy=Integer.toString(dayOfMonth);
                        String monthh=Integer.toString(month+1);
                        String yearr=Integer.toString(year);

                       /* currentDateDay.setText(dayy);
                        currentDateMonth.setText(monthh);
                        currentDateYear.setText(yearr);*/

                        if(dayy.equals("1") || dayy.equals("2") || dayy.equals("3") || dayy.equals("4") || dayy.equals("5") || dayy.equals("6") || dayy.equals("7") || dayy.equals("8") || dayy.equals("9")){
                            dayy="0"+dayy;
                        }
                        if(monthh.equals("1") || monthh.equals("2") || monthh.equals("3") || monthh.equals("4") || monthh.equals("5") || monthh.equals("6") || monthh.equals("7") || monthh.equals("8") || monthh.equals("9")){
                            monthh="0"+monthh;
                        }

                        iDontKnowTextView.setText(dayy+"/"+monthh+"/"+yearr);
                        lastDonationTextView.setText("I don't know");
                    }
                },currentYear,currentMonth,currentDay);

        date_Picker_Dialog.show();
    }


    // Donor Store in Database.............................................................
    private void donorStoreInDatabase(String rn) {

        donorName=name.getText().toString();
        donorBloodGroup=selectBloodGroup.getSelectedItem().toString();
        donorDistrict=select_city.getText().toString();
        donorPhoneNumber=phoneNumber.getText().toString();
        //String donorEmail=email.getText().toString();
        //String donorGender="";
        donorDepartment=department.getText().toString();
        donorSession=session.getText().toString();
        donorLastDonationDate=iDontKnowTextView.getText().toString();

        // Toast.makeText(this, "......"+donorDistrict, Toast.LENGTH_SHORT).show();
        //............validation all field.......................
        if(donorName.isEmpty()){
            name.setError("please enter name!");
            name.requestFocus();
            return;
        }
        if(donorDistrict.isEmpty()){
            select_city.setError("empty!");
            select_city.requestFocus();
            return;
        }
        if(donorPhoneNumber.isEmpty()){
            phoneNumber.setError("please enter phone number!");
            phoneNumber.requestFocus();
            return;
        }
        if(donorPhoneNumber.length()!=11){
            phoneNumber.setError("Invalid phone number(length must be 11)!");
            phoneNumber.requestFocus();
            return;
        }

        // internet connection check;
        if(!isConnected(BeDonorActivity.this)){
            buildDialog(BeDonorActivity.this).show();
        }else{

            Intent intent = new Intent(BeDonorActivity.this,BeDonor2Activity.class);

            intent.putExtra("rn",rn);
            intent.putExtra("name",donorName);
            intent.putExtra("bloodGroup",donorBloodGroup);
            intent.putExtra("district",donorDistrict);
            intent.putExtra("phone",donorPhoneNumber);
            intent.putExtra("department",donorDepartment);
            intent.putExtra("session",donorSession);
            intent.putExtra("lastDonationDate",donorLastDonationDate);

            startActivity(intent);
            finish();

            /*String key=databaseReference.push().getKey();

            DonorClass donorClass = new DonorClass(rn,key,donorName,donorBloodGroup,donorPhoneNumber,donorDistrict,donorDepartment,donorSession,donorLastDonationDate);
            //DonorIdClass donorIdClass=new DonorIdClass(rn,key,donorPhoneNumber);
            DonorClass profileClass = new DonorClass(rn,key,donorName,donorBloodGroup,donorPhoneNumber,donorDistrict,donorDepartment,donorSession,donorLastDonationDate);

            databaseReference.child(donorBloodGroup).child(donorDistrict).child(key).setValue(donorClass);
            //databaseReference1.child(donorBloodGroup).child(donorDistrict).child(key).setValue(donorIdClass);
            databaseReference1.child(donorPhoneNumber).child(key).setValue(profileClass);

            Toast.makeText(getApplicationContext(),"Donor Add Successfull !",Toast.LENGTH_SHORT).show();

            clearAllFieldValue(); // clear method call..............

            Intent intent = new Intent(BeDonorActivity.this,MainActivity.class);
            intent.putExtra("checkPoint","finish");

            AddDonorActivity.appsUserMobileNumber=donorPhoneNumber;*/

           // startActivity(intent);


        }
    }

    // check District Name validation method......................................................
    private int checkDistrictName(String donorDistrict) {

        int flag=0;
        int i=0;
        for(i=0;i<districtNameArray.length;i++){

            String temp=districtNameArray[i];
            int checkValue=donorDistrict.compareToIgnoreCase(temp);

            if(checkValue==0){
                flag=1;
                break;
            }
        }

        return flag;
    }

    // clear all field value......................................................

    private void clearAllFieldValue() {

        name.setText("");
        //email.setText("");
        phoneNumber.setText("");
        select_city.setText("");
        department.setText("");
        session.setText("");
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