package com.example.rifat.bloodbankapps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private List<BloodBankClass> bloodBankList;
    private BloodBankCustomAdapter bloodBankCustomAdapter;

    private Spinner districtSpinner;
    private String[] districtArrayBloodBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        districtSpinner=(Spinner)findViewById(R.id.bloodBankDistrictSpinner_id);
        districtArrayBloodBank=getResources().getStringArray(R.array.bloodbankDistrictArray);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.bloodbankdistrictnamespinersamplelayout,R.id.TextViewSample_id,districtArrayBloodBank);
        districtSpinner.setAdapter(adapter);

        listView=(ListView)findViewById(R.id.bloodBankListView_id);
        bloodBankList=new ArrayList<>();
        bloodBankCustomAdapter = new BloodBankCustomAdapter(BloodBankActivity.this,bloodBankList);

        String selectedDistrict=districtSpinner.getSelectedItem().toString();

        final DatabaseReference myRefBB=FirebaseDatabase.getInstance().getReference("BloodBankTable");

        myRefBB.child(selectedDistrict).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodBankList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    BloodBankClass bloodBankClass = dataSnapshot1.getValue(BloodBankClass.class);

                    bloodBankList.add(bloodBankClass);
                }
                listView.setAdapter(bloodBankCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {



    }


}
