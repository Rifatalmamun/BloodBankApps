package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    private EditText name,email,phoneNumber;
    private RadioGroup genderRadioGroup;
    private RadioButton selectButton;
    private Spinner selectBloodGroup;
    private AutoCompleteTextView districtName;
    private String[] bloodGroupArray;
    private String[] districtNameArray;
    private Button beDonorSubmitButton;

    Random r;
    public int min,max,output;

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
        districtName=(AutoCompleteTextView)findViewById(R.id.district_id);
        phoneNumber=(EditText)findViewById(R.id.phoneNumberEditText_id);
        genderRadioGroup=(RadioGroup)findViewById(R.id.radioGroup_id);
        email=(EditText)findViewById(R.id.emailEditText_id);
        beDonorSubmitButton=(Button)findViewById(R.id.beDonorSubmitButton_id);

        // Blood Group Spinner......................................................................

        bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.bloodgroupsamplelayout,R.id.TextViewSample_id,bloodGroupArray);
        selectBloodGroup.setAdapter(adapter1);

        // District Name Autocomplete TextView......................................................

        districtNameArray=getResources().getStringArray(R.array.DistrictArray);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districtNameArray);
        districtName.setThreshold(1);
        districtName.setAdapter(adapter2);

        // set onclick listener...............................................................
        beDonorSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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

            donorStoreInDatabase(random_number);

        }
    }








    // Donor Store in Database.............................................................
    private void donorStoreInDatabase(String rn) {

        String donorName=name.getText().toString();
        String donorBloodGroup=selectBloodGroup.getSelectedItem().toString();
        String donorDistrict=districtName.getText().toString();
        String donorPhoneNumber=phoneNumber.getText().toString();
        String donorEmail=email.getText().toString();
        String donorGender="";

        int selectableId=genderRadioGroup.getCheckedRadioButtonId();
        selectButton=(RadioButton)findViewById(selectableId);

        if(genderRadioGroup.getCheckedRadioButtonId()==R.id.maleRadioButton_id){
            donorGender="Male";
        }
        else if(genderRadioGroup.getCheckedRadioButtonId()==R.id.femaleRadioButton_id){
            donorGender="Female";
        }

        //............validation all field.......................
        if(donorName.isEmpty()){
            name.setError("please enter name!");
            name.requestFocus();
            return;
        }
        if(donorDistrict.isEmpty()){
            districtName.setError("please enter district name!");
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
        if(donorEmail.isEmpty()){
            email.setError("please enter email address!");
            email.requestFocus();
            return;
        }

        int check=checkDistrictName(donorDistrict);

        if(check==0){
            districtName.setError("District Spelling error!!!");
            districtName.requestFocus();
            return;
        }

            String key=databaseReference.push().getKey();


            DonorClass donorClass = new DonorClass(rn,key,donorName,donorBloodGroup,donorPhoneNumber,donorEmail,donorDistrict,donorGender);
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
        email.setText("");
        phoneNumber.setText("");
        districtName.setText("");
    }
}
