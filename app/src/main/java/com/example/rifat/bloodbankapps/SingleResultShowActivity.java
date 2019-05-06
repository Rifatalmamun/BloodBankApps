package com.example.rifat.bloodbankapps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingleResultShowActivity extends AppCompatActivity {

    private ListView listView;
    private List<DonorClass> donorList;
    private SingleCustomAdapter singleCustomAdapter;

    int countDonor=0;
    private Button alertBuilderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_result_show);

        this.setTitle("Single Result");

        String catchSinglePhoneNumber=getIntent().getExtras().getString("singlePhoneNumber");
        String catchSingleBloodGroup=getIntent().getExtras().getString("singleBloodGroup");
        String catchSingleDistrictName=getIntent().getExtras().getString("singleDistrictName");


        listView=(ListView)findViewById(R.id.singleBloodResultShowListView_id);
        donorList = new ArrayList<>();
        singleCustomAdapter=new SingleCustomAdapter(SingleResultShowActivity.this,donorList);


        //......................database access...............................


        final DatabaseReference singlemyRef=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");

        singlemyRef.child(catchSingleBloodGroup).child(catchSingleDistrictName)
                .orderByChild("donor_phoneNumber").equalTo(catchSinglePhoneNumber).
               addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                donorList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);

                    donorList.add(donorClass);
                }

                listView.setAdapter(singleCustomAdapter);

               /* if(dataSnapshot.exists())
                    countDonor=(int) dataSnapshot.getChildrenCount();
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // custom adapter find variable.............................................

    }
}
