package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static int flag = 0;
    static int flag2=0;

    private EditText updateName,updatePhone;
    private Spinner updateBloodgroupSpinner,updateSessionSpinner;
    private ImageView updateDistrictSpinner,updateDepartmentSpinner;
    private TextView updateLastDonationDateTextVeiw,updateIdontKnowTextVeiw;
    private TextView updateDistrictNameTextView,updateDepartmentNameTextView,updateSessionFieldTextView;

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



    String name="";
    String district="";
    String bloodGroup="";
    String phone="";
    String departmetn="";
    String session="";
    String lastDate="";



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
        updateSessionSpinner=(Spinner)findViewById(R.id.updateSessionSpinner_id);

        updateLastDonationDateTextVeiw=(TextView)findViewById(R.id.updateLastDonationTextView_id);
        updateIdontKnowTextVeiw=(TextView)findViewById(R.id.updateIDontKnowTextView_id);



        // first step: ............................................................

            try{
                final String catchName=getIntent().getExtras().getString("catchDonorName");
                final String catchBgroup=getIntent().getExtras().getString("catchDonorBloodGroup");
                final String catchDistrict=getIntent().getExtras().getString("catchDonorDistrict");
                final String catchPNumber=getIntent().getExtras().getString("catchDonorNumber");
                final String catchDept=getIntent().getExtras().getString("catchDonorDepartment");
                final String catchSession=getIntent().getExtras().getString("catchDonorSession");
                final String catchLastDdate=getIntent().getExtras().getString("catchDonorDonationDate");

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

        }catch (Exception e){
           // Toast.makeText(this, "exception"+e, Toast.LENGTH_LONG).show();
            }
        }


        updateDistrictSpinner.setOnClickListener(this);
        updateDepartmentSpinner.setOnClickListener(this);
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

            startActivity(intent);
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

            startActivity(intent);
        }
    }
}
