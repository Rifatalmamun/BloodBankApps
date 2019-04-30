package com.example.rifat.bloodbankapps;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchBloodResultShowActivity extends AppCompatActivity{

    // declaring all variables...........................
    private ListView listView;
    private List<DonorClass> donorList;
    private ResultShowCustomAdapter resultShowCustomAdapter;



    // text................
    private ArrayList<String> mylist=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_result_show);


        // 2 ta string k catch korlam....................
        String catchBloodGroup=getIntent().getExtras().getString("SendBloodGroup");
        String catchDistrictName=getIntent().getExtras().getString("SendDistrictName");


        // finding all variabls.........................

        listView=(ListView)findViewById(R.id.searchBloodResultShowListView_id);
        donorList = new ArrayList<>();
        resultShowCustomAdapter=new ResultShowCustomAdapter(SearchBloodResultShowActivity.this,donorList);

        //query_BasedOnBloodGroup.addListenerForSingleValueEvent(valueEventListener);

        final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("DonorList");

           myRef.child(catchBloodGroup).child(catchDistrictName).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   donorList.clear();

                   for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
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

           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   int pos=parent.getPositionForView(view);  // list item er position gulo find korlam

                    String namePosition=mylist.get(pos*3);

                   Toast.makeText(SearchBloodResultShowActivity.this, "Pos: "+pos, Toast.LENGTH_SHORT).show();
                   //Toast.makeText(SearchBloodResultShowActivity.this, "Name: "+namePosition, Toast.LENGTH_SHORT).show();
               }
           });
    }
}
