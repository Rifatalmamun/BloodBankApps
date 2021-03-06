package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity implements View.OnClickListener {

    private Menu menu;

    private ListView listView;
    private List<AmbulanceClass> ambulanceList;
    private AmbulanceCustomAdapter ambulanceCustomAdapter;

    private String final_district_selection="";
    private int exceptionFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        this.setTitle("Ambulance");

        try{
            final_district_selection=getIntent().getExtras().getString("districtNameCatch");
            exceptionFlag=0;

        }catch(Exception e){
            exceptionFlag=1;
            final_district_selection="Jessore";
        }
        //............floaditing action button.............................
        FloatingActionButton fab = findViewById(R.id.fab_id);
        fab.setOnClickListener(this);
        fab.setVerticalScrollbarPosition(0);

        listView=(ListView)findViewById(R.id.ambulanceListView_id);
        ambulanceList=new ArrayList<>();
        ambulanceCustomAdapter = new AmbulanceCustomAdapter(AmbulanceActivity.this,ambulanceList);

        final DatabaseReference myRefBB=FirebaseDatabase.getInstance().getReference("AmbulanceTable");

        myRefBB.child(final_district_selection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ambulanceList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    AmbulanceClass ambulanceClass = dataSnapshot1.getValue(AmbulanceClass.class);

                    ambulanceList.add(ambulanceClass);
                }
                listView.setAdapter(ambulanceCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab_id){
            Intent intent = new Intent(AmbulanceActivity.this,AddAmbulanceActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ambulance_page_menu_layout,menu);

        this.menu = menu;
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_selectLocationForAmbulance_id){
            Toast.makeText(this, "clicked!!", Toast.LENGTH_SHORT).show();

            Intent intent =new Intent(AmbulanceActivity.this,districtList_For_AmbulanceSearch.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(!final_district_selection.equals("") && exceptionFlag!=1){
            MenuItem menuItem = menu.findItem(R.id.action_selectLocationForAmbulance_id);

            menuItem.setTitle(final_district_selection);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateMenuTitles(String menuName){
        MenuItem menuItem = menu.findItem(R.id.action_selectLocation_id);

        menuItem.setTitle(menuName);
    }

}
