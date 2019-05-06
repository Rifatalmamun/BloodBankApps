package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchBloodResultShowActivity extends AppCompatActivity{

    // declaring all variables...........................
    private ListView listView;
    private List<DonorClass> donorList;
    private ResultShowCustomAdapter resultShowCustomAdapter;

    // text................
    public int countDonor=0;
    public String id="";

    public String[] phoneString;
    public ArrayList<String> phoneArrayList=new ArrayList<String>();
    StringBuilder stringBuilder=new StringBuilder();
    int length=0,i=0;
    public String[] indexString;
    public String ss;
    public String tt;


    private List<DonorClass>donorPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_result_show);

        this.setTitle("Search Result");

        // 2 ta string k catch korlam....................
        final String catchBloodGroup=getIntent().getExtras().getString("SendBloodGroup");
        final String catchDistrictName=getIntent().getExtras().getString("SendDistrictName");



        // finding all variabls.........................

        listView=(ListView)findViewById(R.id.searchBloodResultShowListView_id);
        donorList = new ArrayList<>();
        resultShowCustomAdapter=new ResultShowCustomAdapter(SearchBloodResultShowActivity.this,donorList);

        //query_BasedOnBloodGroup.addListenerForSingleValueEvent(valueEventListener);

        final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("DonorDetailsTable");

        myRef.child(catchBloodGroup).child(catchDistrictName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                donorList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);

                    //phoneString[i++]=donorClass.getDonor_phone_number();

                    stringBuilder.append(donorClass.getDonor_phoneNumber());

                    donorList.add(donorClass);

                }

                listView.setAdapter(resultShowCustomAdapter);

                length=stringBuilder.length();

             // count total child
               /* if(dataSnapshot.exists()){
                    countDonor=(int) dataSnapshot.getChildrenCount();
                }*/

                ss=stringBuilder.toString();


             //Toast.makeText(SearchBloodResultShowActivity.this, "Total number: "+ss, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos=parent.getPositionForView(view);  // list item er position gulo find korlam

                int s_index=(pos*11);
                int e_index=((pos+1)*11);

                tt=ss.substring(s_index,e_index);

                //Toast.makeText(SearchBloodResultShowActivity.this, "Click number: "+tt, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SearchBloodResultShowActivity.this,SingleResultShowActivity.class);

                intent.putExtra("singlePhoneNumber",tt);
                intent.putExtra("singleBloodGroup",catchBloodGroup);
                intent.putExtra("singleDistrictName",catchDistrictName);

                String ref = myRef.child(catchBloodGroup).child(catchDistrictName).getKey();

                Toast.makeText(getApplicationContext(),"Ref: "+ref,Toast.LENGTH_SHORT).toString();

                startActivity(intent);


                //Toast.makeText(SearchBloodResultShowActivity.this, "Pos: "+position, Toast.LENGTH_SHORT).show();

            }
        });

    }
}