package com.example.rifat.bloodbankapps;

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

public class BeDonorActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference; // for firebase

    private EditText name,email,phoneNumber;
    private RadioGroup genderRadioGroup;
    private RadioButton selectButton;
    private Spinner selectBloodGroup;
    private AutoCompleteTextView districtName;
    private String[] bloodGroupArray;
    private String[] districtNameArray;
    private Button beDonorSubmitButton;

    public int countDonor=0;
    public int countDonorfor_ARaj=0;
    public int getCountDonorfor_ORaj=0;
    String countDonors="";
    public String k="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_donor);

        databaseReference=FirebaseDatabase.getInstance().getReference("DonorList");
        this.setTitle("Be a Donor page");
        //..............................................................................................



        //...............................................................................................

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

            donorStoreInDatabase();

        }
    }
    // Donor Store in Database.............................................................
    private void donorStoreInDatabase() {

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

        //................................

        if(donorBloodGroup.equals("A+") && donorDistrict.equals("Rajshahi")){
            //countDonorfor_ARaj=countDonorfor_ARaj+1;

            final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("DonorList");

            myRef.child(donorBloodGroup).child(donorDistrict).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //donorList.clear();

                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);
                            }

                            // count total child
                            if(dataSnapshot.exists()){
                                countDonorfor_ARaj=(int) dataSnapshot.getChildrenCount();
                            }

                            //Toast.makeText(SearchBloodResultShowActivity.this, "Total donor: "+countDonor, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            //countDonorfor_ARaj=countDonorfor_ARaj;
            countDonors=Integer.toString(countDonorfor_ARaj);

        }
        if(donorBloodGroup.equals("O+") && donorDistrict.equals("Rajshahi")){

            final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("DonorList");

            myRef.child(donorBloodGroup).child(donorDistrict).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //donorList.clear();

                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);
                            }

                            // count total child
                            if(dataSnapshot.exists()){
                                getCountDonorfor_ORaj=(int) dataSnapshot.getChildrenCount();
                            }

                            //Toast.makeText(SearchBloodResultShowActivity.this, "Total donor: "+countDonor, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

           // getCountDonorfor_ORaj=getCountDonorfor_ORaj;
            countDonors=Integer.toString(getCountDonorfor_ORaj);


        }


        //................................



            String key=databaseReference.push().getKey();


            DonorClass donorClass = new DonorClass(countDonors,key,donorName,donorBloodGroup,donorPhoneNumber,donorEmail,donorDistrict,donorGender);

            databaseReference.child(donorBloodGroup).child(donorDistrict).push().setValue(donorClass);

            Toast.makeText(getApplicationContext(),"Donor Add Successfull !",Toast.LENGTH_SHORT).show();

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
}
