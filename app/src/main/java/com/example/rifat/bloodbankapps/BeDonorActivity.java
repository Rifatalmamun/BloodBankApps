package com.example.rifat.bloodbankapps;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.Random;

public class BeDonorActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference,databaseReference1; // for firebase

    private EditText name,phoneNumber,department,session;
    private Spinner selectBloodGroup;
    private ImageView selectCitySpinner;
    private AutoCompleteTextView districtName;
    private String[] bloodGroupArray;
    private String[] districtNameArray;
    private Button beDonorSubmitButton;
    private Button datePickerButton;
    private TextView lastDonationTextView,iDontKnowTextView,select_city;

    private DatePickerDialog date_Picker_Dialog;

    Random r;
    public int min,max,output;
    String catchSelectedCity="";
    Info in;
    static int flage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_donor);



        databaseReference=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");
        databaseReference1=FirebaseDatabase.getInstance().getReference("DonorIdTable");
        this.setTitle("Be a Donor page");

        r=new Random();
        // finding all variables....................................................................

        name=(EditText)findViewById(R.id.nameEditText_id);
        selectBloodGroup=(Spinner)findViewById(R.id.selectBloodGroup_id);
        selectCitySpinner=(ImageView) findViewById(R.id.selectCitySpinner_id);
        //districtName=(AutoCompleteTextView)findViewById(R.id.district_id);
        phoneNumber=(EditText)findViewById(R.id.phoneNumberEditText_id);
        //genderRadioGroup=(RadioGroup)findViewById(R.id.radioGroup_id);
        //email=(EditText)findViewById(R.id.emailEditText_id);
        department=(EditText)findViewById(R.id.departmentEditText_id);
        session=(EditText)findViewById(R.id.sessionEditText_id);
        beDonorSubmitButton=(Button)findViewById(R.id.beDonorSubmitButton_id);
        datePickerButton=(Button)findViewById(R.id.datePicker_id);
        lastDonationTextView=(TextView)findViewById(R.id.lastDonationDateTextView_id);
        iDontKnowTextView=(TextView)findViewById(R.id.iDontKnowTextView_id);
        select_city=(TextView)findViewById(R.id.selectCity_id);

        select_city.setText(catchSelectedCity);

        // Blood Group Spinner......................................................................

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.bloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        selectBloodGroup.setAdapter(adapter1);

        // District Name Autocomplete TextView......................................................

        /*districtNameArray=getResources().getStringArray(R.array.DistrictArray);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districtNameArray);
        districtName.setThreshold(1);
        districtName.setAdapter(adapter2);*/

        // set onclick listener...............................................................
        beDonorSubmitButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);
        selectCitySpinner.setOnClickListener(this);
        //iDontKnowTextView.setOnClickListener(this);

        if(flage > 0){
            try {
                catchSelectedCity=getIntent().getExtras().getString("selectedCity");
                select_city.setText(catchSelectedCity);
               // Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.selectCitySpinner_id){

            // go to city name list page and select a city name..........
            Intent intent  = new Intent(BeDonorActivity.this,currentLocation.class);
            flage=1;
            startActivity(intent);

            finish();
        }

        if(v.getId()==R.id.datePicker_id){
            openDatePicker();
            //Toast.makeText(getApplicationContext(), "clicked button", Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.beDonorSubmitButton_id){



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

                        iDontKnowTextView.setText(dayy+"/"+monthh+"/"+yearr);
                    }
                },currentYear,currentMonth,currentDay);

        date_Picker_Dialog.show();
    }


    // Donor Store in Database.............................................................
    private void donorStoreInDatabase(String rn) {

        String donorName=name.getText().toString();
        String donorBloodGroup=selectBloodGroup.getSelectedItem().toString();
        String donorDistrict=select_city.getText().toString();
        String donorPhoneNumber=phoneNumber.getText().toString();
        //String donorEmail=email.getText().toString();
        //String donorGender="";
        String donorDepartment=department.getText().toString();
        String donorSession=session.getText().toString();
        String donorLastDonationDate=iDontKnowTextView.getText().toString();

       // Toast.makeText(this, "......"+donorDistrict, Toast.LENGTH_SHORT).show();
        //............validation all field.......................
        if(donorName.isEmpty()){
            name.setError("please enter name!");
            name.requestFocus();
            return;
        }
        if(donorDistrict.isEmpty()){
            districtName.setError("please select district name!");
            districtName.requestFocus();
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


        int check=checkDistrictName(donorDistrict);

        if(check==0){
            districtName.setError("District Spelling error!!!");
            districtName.requestFocus();
            return;
        }

            String key=databaseReference.push().getKey();


            DonorClass donorClass = new DonorClass(rn,key,donorName,donorBloodGroup,donorPhoneNumber,donorDistrict,donorDepartment,donorSession,donorLastDonationDate);
            DonorIdClass donorIdClass=new DonorIdClass(rn,key,donorPhoneNumber);

            databaseReference.child(donorBloodGroup).child(donorDistrict).push().setValue(donorClass);
            databaseReference1.child(donorBloodGroup).child(donorDistrict).push().setValue(donorIdClass);

            Toast.makeText(getApplicationContext(),"Donor Add Successfull !",Toast.LENGTH_SHORT).show();

            clearAllFieldValue();

            Intent intent = new Intent(BeDonorActivity.this,MainActivity.class);
            startActivity(intent);

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
        districtName.setText("");
        department.setText("");
        session.setText("");
    }
}
