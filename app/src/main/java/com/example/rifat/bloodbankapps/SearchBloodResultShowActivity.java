package com.example.rifat.bloodbankapps;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchBloodResultShowActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    // declaring all variables...........................
    private ListView listView;
    private List<DonorClass> donorList;
    private ResultShowCustomAdapter resultShowCustomAdapter;


    private ImageView donorPhoneIcon,donorEmailIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_result_show);

        //donorPhoneIcon=(ImageView)findViewById(R.id.donorPhoneIcon_id);
        //donorEmailIcon=(ImageView)findViewById(R.id.donorEmailIcon_id);

        //donorPhoneIcon.setOnClickListener(this);
        //donorEmailIcon.setOnClickListener(this);

        // 2 ta string k catch korlam....................
        String catchBloodGroup=getIntent().getExtras().getString("SendBloodGroup");
        String catchDistrictName=getIntent().getExtras().getString("SendDistrictName");

        databaseReference=FirebaseDatabase.getInstance().getReference("DonorList");



        Query query_BasedOnBloodGroup =FirebaseDatabase.getInstance().getReference("DonorList")
                .orderByChild("donor_blood_group").orderByChild("donor_district")
                .equalTo(catchBloodGroup).equalTo(catchDistrictName);


        // finding all variabls.........................

        listView=(ListView)findViewById(R.id.searchBloodResultShowListView_id);
        donorList = new ArrayList<>();
        resultShowCustomAdapter=new ResultShowCustomAdapter(SearchBloodResultShowActivity.this,donorList);

        query_BasedOnBloodGroup.addListenerForSingleValueEvent(valueEventListener);

    }

   ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            donorList.clear();

            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
            {
                DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);
                donorList.add(donorClass);
            }

            listView.setAdapter(resultShowCustomAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



 /* @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                donorList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);
                    donorList.add(donorClass);
                }

                listView.setAdapter(resultShowCustomAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }*/


    @Override
    public void onClick(View v) {


     /*   if(v.getId()==R.id.donorPhoneIcon_id){
            Toast.makeText(getApplicationContext(),"Phone Icon Clicked",Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.donorEmailIcon_id){
            Toast.makeText(getApplicationContext(), "Email Icon Clicked", Toast.LENGTH_SHORT).show();
        }*/


    }
}
