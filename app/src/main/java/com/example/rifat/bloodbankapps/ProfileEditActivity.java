package com.example.rifat.bloodbankapps;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference updateDonorDetailsTableReference,updateMyProfileTableReference,profileRef,queryRef;

    public static int flag = 0;
    static int flag2=0;

    private EditText updateName,updatePhone;
    private Spinner updateBloodgroupSpinner;
    private ImageView updateDistrictSpinner,updateDepartmentSpinner,updateSessionSpinner;
    private TextView updateLastDonationDateTextVeiw,updateIdontKnowTextVeiw;
    private TextView updateDistrictNameTextView,updateDepartmentNameTextView,updateSessionFieldTextView;
    private Button datePickerButton;
    private DatePickerDialog date_Picker_Dialog;
    private Button updateButton,cancleButton;

    private String[] bloodGroupArray;
    private int bloodPosition;

    private String checkPoint="";

    // sotrage section
    private String catchSelectedDistrict="";
    private String catchUN="";
    private String catchBG="";
    private String catchPN="";
    private String catchDP="";
    private String catchSES="";
    private String catchLastDate="";
    private String catchKEY="";
    private String catchRN="";
    private String parentGroup="";
    private String parentDist="";
    private String parentPhoneNumber="";


    String name="";
    String district="";
    String bloodGroup="";
    String phone="";
    String departmetn="";
    String session="";
    String lastDate="";
    String kEy="";





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);



        //flag=0;

        // finding all field...........................................

        updateName=(EditText)findViewById(R.id.updateNameEditText_id);
        updatePhone=(EditText)findViewById(R.id.updatePhoneNumbeEditText_id);

        updateBloodgroupSpinner=(Spinner)findViewById(R.id.updateBloodGroupSpinner_id);
        updateDistrictNameTextView=(TextView)findViewById(R.id.updateDistrictFieldTextView_id);
        updateDistrictSpinner=(ImageView) findViewById(R.id.updateDistrictSpinner_id);
        updateDepartmentNameTextView=(TextView)findViewById(R.id.updateDepartmentFieldTextView_id);
        updateDepartmentSpinner=(ImageView)findViewById(R.id.updateDepartmentSpinner_id);
        updateSessionFieldTextView=(TextView)findViewById(R.id.updateSessionFieldTextView_id);
        updateSessionSpinner=(ImageView)findViewById(R.id.updateSessionSpinner_id);

        updateLastDonationDateTextVeiw=(TextView)findViewById(R.id.updateLastDonationTextView_id);
        updateIdontKnowTextVeiw=(TextView)findViewById(R.id.updateIDontKnowTextView_id);

        datePickerButton=(Button)findViewById(R.id.datePicker_id);
        updateButton=(Button)findViewById(R.id.updateButton_id);
        cancleButton=(Button)findViewById(R.id.cancleButton_id);

        // first step: ............................................................

            try{
                final String catchName=getIntent().getExtras().getString("catchDonorName");
                final String catchBgroup=getIntent().getExtras().getString("catchDonorBloodGroup");
                parentGroup=catchBgroup;
                final String catchDistrict=getIntent().getExtras().getString("catchDonorDistrict");
                parentDist=catchDistrict;
                final String catchPNumber=getIntent().getExtras().getString("catchDonorNumber");
                parentPhoneNumber=catchPNumber;
                final String catchDept=getIntent().getExtras().getString("catchDonorDepartment");
                final String catchSession=getIntent().getExtras().getString("catchDonorSession");
                final String catchLastDdate=getIntent().getExtras().getString("catchDonorDonationDate");
                String catchDonorKey=getIntent().getExtras().getString("catchDonorKey");
                catchKEY=catchDonorKey;
                String catchRandomNumbeR=getIntent().getExtras().getString("catchRandomNumber");
                catchRN=catchRandomNumbeR;

                updateName.setText(catchName);
                updateDistrictNameTextView.setText(catchDistrict);
                updatePhone.setText(catchPNumber);
                updateDepartmentNameTextView.setText(catchDept);
                updateSessionFieldTextView.setText(catchSession);
                updateIdontKnowTextVeiw.setText(catchLastDdate);

                // ebar Blood Group spinner ta handle korbo..........................

                bloodGroupArray=getResources().getStringArray(R.array.bloodArray);
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.bloodgroupsamplelayoutforupdateprofile,R.id.TextViewSample_id,bloodGroupArray);
                updateBloodgroupSpinner.setAdapter(adapter1);

                for(int i = 0;i<8;i++){
                    if(catchBgroup.equals(bloodGroupArray[i])){
                        bloodPosition = i;
                        break;
                    }
                }
                updateBloodgroupSpinner.setSelection(bloodPosition);
            }catch(Exception e){
               // Toast.makeText(this, "load data"+e, Toast.LENGTH_LONG).show();
            }

            ////////////////////////////////////////////////////////

        if(flag > 0){
        try{
            catchSelectedDistrict = getIntent().getExtras().getString("selectedCity");
            updateDistrictNameTextView.setText(catchSelectedDistrict);

            catchUN=getIntent().getExtras().getString("UN");
            updateName.setText(catchUN);

            catchBG=getIntent().getExtras().getString("P");
            int posi = Integer.parseInt(catchBG);
            updateBloodgroupSpinner.setSelection(posi);

            catchPN=getIntent().getExtras().getString("PN");
            updatePhone.setText(catchPN);
            catchDP=getIntent().getExtras().getString("DEPT");
            updateDepartmentNameTextView.setText(catchDP);
            catchSES=getIntent().getExtras().getString("SESS");
            updateSessionFieldTextView.setText(catchSES);

            catchLastDate=getIntent().getExtras().getString("LAST");
            updateIdontKnowTextVeiw.setText(catchLastDate);

            catchKEY=getIntent().getExtras().getString("KEY");
            catchRN=getIntent().getExtras().getString("RANDOM");
            parentGroup=getIntent().getExtras().getString("PARENTGROUP");
            parentDist=getIntent().getExtras().getString("PARENTDISTRICT");
            parentPhoneNumber=getIntent().getExtras().getString("PARENTPHONENUMBER");

        }catch (Exception e){
           // Toast.makeText(this, "exception"+e, Toast.LENGTH_LONG).show();
            }
        }


        updateDistrictSpinner.setOnClickListener(this);
        updateDepartmentSpinner.setOnClickListener(this);
        updateSessionSpinner.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);

        updateButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.updateDistrictSpinner_id){
            // take userName,userBloodGroup
            name=updateName.getText().toString();
            bloodGroup=updateBloodgroupSpinner.getSelectedItem().toString();
            phone=updatePhone.getText().toString();
            departmetn=updateDepartmentNameTextView.getText().toString();
            session=updateSessionFieldTextView.getText().toString();
            lastDate=updateIdontKnowTextVeiw.getText().toString();

            flag=flag+1;

            Intent intent = new Intent(ProfileEditActivity.this,currentlocation3.class);
            intent.putExtra("sendName",name);
            intent.putExtra("sendBloodGroup",bloodGroup);
            intent.putExtra("sendPhone",phone);
            intent.putExtra("sendDept",departmetn);
            intent.putExtra("sendSession",session);
            intent.putExtra("sendLastDate",lastDate);
            intent.putExtra("sendKEY",catchKEY);
            intent.putExtra("sendRANDOM",catchRN);
            intent.putExtra("sendPARENTGROUP",parentGroup);
            intent.putExtra("sendPARENTDISTRICT",parentDist);
            intent.putExtra("sendPARENTPHONENUMBER",parentPhoneNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.updateDepartmentSpinner_id){

           // take all to pass departmentlist activity.................

            // take userName,userBloodGroup
            name=updateName.getText().toString();
            bloodGroup=updateBloodgroupSpinner.getSelectedItem().toString();
            phone=updatePhone.getText().toString();
            district=updateDistrictNameTextView.getText().toString();
            //departmetn=updateDepartmentNameTextView.getText().toString();
            session=updateSessionFieldTextView.getText().toString();
            lastDate=updateIdontKnowTextVeiw.getText().toString();

            flag=flag+1;

            Intent intent = new Intent(ProfileEditActivity.this,departmentList.class);

            intent.putExtra("sendName",name);
            intent.putExtra("sendBloodGroup",bloodGroup);
            intent.putExtra("sendPhone",phone);
            intent.putExtra("sendDist",district);
            intent.putExtra("sendSession",session);
            intent.putExtra("sendLastDate",lastDate);
            intent.putExtra("sendKEY",catchKEY);
            intent.putExtra("sendRANDOM",catchRN);
            intent.putExtra("sendPARENTGROUP",parentGroup);
            intent.putExtra("sendPARENTDISTRICT",parentDist);
            intent.putExtra("sendPARENTPHONENUMBER",parentPhoneNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.updateSessionSpinner_id){

            name=updateName.getText().toString();
            bloodGroup=updateBloodgroupSpinner.getSelectedItem().toString();
            phone=updatePhone.getText().toString();
            district=updateDistrictNameTextView.getText().toString();
            departmetn=updateDepartmentNameTextView.getText().toString();
            //session=updateSessionFieldTextView.getText().toString();
            lastDate=updateIdontKnowTextVeiw.getText().toString();

            flag=flag+1;

            Intent intent = new Intent(ProfileEditActivity.this,SessionList.class);

            intent.putExtra("sendName",name);
            intent.putExtra("sendBloodGroup",bloodGroup);
            intent.putExtra("sendPhone",phone);
            intent.putExtra("sendDist",district);
            intent.putExtra("sendDept",departmetn);
            intent.putExtra("sendLastDate",lastDate);
            intent.putExtra("sendKEY",catchKEY);
            intent.putExtra("sendRANDOM",catchRN);
            intent.putExtra("sendPARENTGROUP",parentGroup);
            intent.putExtra("sendPARENTDISTRICT",parentDist);
            intent.putExtra("sendPARENTPHONENUMBER",parentPhoneNumber);

            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.datePicker_id){
            openDatePicker();
            //Toast.makeText(getApplicationContext(), "clicked button", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.updateButton_id){

            /*AddDonorActivity addDonorActivity=new AddDonorActivity();
            String parentBloodGroup= addDonorActivity.getBloodGroup;
            String parentDistrict=addDonorActivity.getDistrict;*/

           /* Toast.makeText(this, "key: "+catchKEY, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Random: "+catchRN, Toast.LENGTH_SHORT).show();*/

            // now take all field value to update this.....................................

            String takeName=updateName.getText().toString();
            final String takeBloodGroup=updateBloodgroupSpinner.getSelectedItem().toString();
            final String takeDistrict=updateDistrictNameTextView.getText().toString();
            String takePhone=updatePhone.getText().toString();
            String takeDepartment=updateDepartmentNameTextView.getText().toString();
            String takeSession=updateSessionFieldTextView.getText().toString();
            String takeLastDonation=updateIdontKnowTextVeiw.getText().toString();

            /*Toast.makeText(this, "Key : "+catchKEY, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Random : "+catchRN, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Name : "+takeName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Previous BG: "+parentGroup, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "BloodGroup : "+takeBloodGroup, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "District : "+takeDistrict, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Previous District: "+parentDist, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Phone : "+takePhone, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Department : "+takeDepartment, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Session : "+takeSession, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "LastDate : "+takeLastDonation, Toast.LENGTH_SHORT).show();*/

            //Toast.makeText(this, "parent Phone : "+parentPhoneNumber, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Update Phone : "+takePhone, Toast.LENGTH_SHORT).show();

            //........................................................................................

           updateDonorDetailsTableReference=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");
           updateMyProfileTableReference=FirebaseDatabase.getInstance().getReference("MyProfileTable");
             /*profileRef=FirebaseDatabase.getInstance().getReference("DonorIdTable");
            // remove first then
            updateDatabaseReference.child(takeBloodGroup).child(takeDistrict).child(catchKEY).removeValue();
            // insert this new data
            DonorClass donorClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);

            updateDatabaseReference.child(takeBloodGroup).child(takeDistrict).push().setValue(donorClass);
            profileRef.child(takePhone).push().setValue(donorClass);*/

            Map<String,Object> updatedvalue = new HashMap<>();
            Map<String,Object> updatedvalue1 = new HashMap<>();

            //Condition 1: bg same, dist same, phone same..................
            if(parentGroup.equals(takeBloodGroup) && parentDist.equals(takeDistrict) && parentPhoneNumber.equals(takePhone)){

                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updateDonorDetailsTableReference.updateChildren(updatedvalue);
                updateMyProfileTableReference.updateChildren(updatedvalue1);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 2: bg same, dist not same, phone same....
            else if(parentGroup.equals(takeBloodGroup) && !parentDist.equals(takeDistrict) && parentPhoneNumber.equals(takePhone)){

                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(parentGroup).child(takeDistrict).child(catchKEY).setValue(dClass);

                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updateMyProfileTableReference.updateChildren(updatedvalue1);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 3: bg not same, dist same, phone same....
            else if(!parentGroup.equals(takeBloodGroup) && parentDist.equals(takeDistrict) && parentPhoneNumber.equals(takePhone)){
                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(takeBloodGroup).child(parentDist).child(catchKEY).setValue(dClass);

                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updateMyProfileTableReference.updateChildren(updatedvalue1);
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 4: bg not same, dist not same, phone same....
            else if(!parentGroup.equals(takeBloodGroup) && !parentDist.equals(takeDistrict) && parentPhoneNumber.equals(takePhone)){
                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(takeBloodGroup).child(takeDistrict).child(catchKEY).setValue(dClass);

                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue1.put(takePhone+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updateMyProfileTableReference.updateChildren(updatedvalue1);
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 5: bg  same, dist  same, phone not same....
            else if(parentGroup.equals(takeBloodGroup) && parentDist.equals(takeDistrict) && !parentPhoneNumber.equals(takePhone)){

                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_name",takeName);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_bloodGroup",takeBloodGroup);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_district",takeDistrict);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_phoneNumber",takePhone);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_department",takeDepartment);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_session",takeSession);
                updatedvalue.put(parentGroup+"/"+parentDist+"/"+catchKEY+"/donor_lastDonationDate",takeLastDonation);

                updateDonorDetailsTableReference.updateChildren(updatedvalue);

                // remove first then insert
                updateMyProfileTableReference.child(parentPhoneNumber).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateMyProfileTableReference.child(takePhone).child(catchKEY).setValue(dClass);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 6: bg  same, dist not same, phone not same....
            else if(parentGroup.equals(takeBloodGroup) && !parentDist.equals(takeDistrict) && !parentPhoneNumber.equals(takePhone)){
                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(parentGroup).child(takeDistrict).child(catchKEY).setValue(dClass);



                // remove first then insert
                updateMyProfileTableReference.child(parentPhoneNumber).child(catchKEY).removeValue();
                DonorClass ddClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateMyProfileTableReference.child(takePhone).child(catchKEY).setValue(ddClass);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 7: bg not same, dist same, phone not same....
            else if(!parentGroup.equals(takeBloodGroup) && parentDist.equals(takeDistrict) && !parentPhoneNumber.equals(takePhone)){
                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(takeBloodGroup).child(parentDist).child(catchKEY).setValue(dClass);


                // remove first then insert
                updateMyProfileTableReference.child(parentPhoneNumber).child(catchKEY).removeValue();
                DonorClass ddClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateMyProfileTableReference.child(takePhone).child(catchKEY).setValue(ddClass);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                AddDonorActivity.appsUserMobileNumber=takePhone;
            }
            //Condition 8: bg not same, dist same, phone not same....
            else if(!parentGroup.equals(takeBloodGroup) && !parentDist.equals(takeDistrict) && !parentPhoneNumber.equals(takePhone)){
                // remove first then insert
                updateDonorDetailsTableReference.child(parentGroup).child(parentDist).child(catchKEY).removeValue();
                DonorClass dClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateDonorDetailsTableReference.child(takeBloodGroup).child(takeDistrict).child(catchKEY).setValue(dClass);


                // remove first then insert
                updateMyProfileTableReference.child(parentPhoneNumber).child(catchKEY).removeValue();
                DonorClass ddClass = new DonorClass(catchRN,catchKEY,takeName,takeBloodGroup,takePhone,takeDistrict,takeDepartment,takeSession,takeLastDonation);
                updateMyProfileTableReference.child(takePhone).child(catchKEY).setValue(ddClass);

                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();

                AddDonorActivity.appsUserMobileNumber=takePhone;
            }


            Intent intent = new Intent(ProfileEditActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.cancleButton_id){

            Intent intent = new Intent(ProfileEditActivity.this,AddDonorActivity.class);
            startActivity(intent);
            finish();
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

                        if(dayy.equals("1") || dayy.equals("2") || dayy.equals("3") || dayy.equals("4") || dayy.equals("5") || dayy.equals("6") || dayy.equals("7") || dayy.equals("8") || dayy.equals("9")){
                            dayy="0"+dayy;
                        }
                        if(monthh.equals("1") || monthh.equals("2") || monthh.equals("3") || monthh.equals("4") || monthh.equals("5") || monthh.equals("6") || monthh.equals("7") || monthh.equals("8") || monthh.equals("9")){
                            monthh="0"+monthh;
                        }

                        updateIdontKnowTextVeiw.setText(dayy+"/"+monthh+"/"+yearr);
                    }
                },currentYear,currentMonth,currentDay);

        date_Picker_Dialog.show();
    }
}
