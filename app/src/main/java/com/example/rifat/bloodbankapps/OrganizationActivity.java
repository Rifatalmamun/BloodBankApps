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

public class OrganizationActivity extends AppCompatActivity implements View.OnClickListener {

    private Menu menu;

    private ListView listView;
    private List<OrganizationClass> organizationList;
    private OrganizationCustomAdapter organizationCustomAdapter;

    private String final_district_selection="";
    private int exceptionFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        this.setTitle("Organization");


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

        listView=(ListView)findViewById(R.id.organizationListView_id);
        organizationList=new ArrayList<>();
        organizationCustomAdapter = new OrganizationCustomAdapter(OrganizationActivity.this,organizationList);

        final DatabaseReference myRefBB=FirebaseDatabase.getInstance().getReference("OrganizationTable");

        myRefBB.child(final_district_selection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                organizationList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    OrganizationClass organizationClass = dataSnapshot1.getValue(OrganizationClass.class);

                    organizationList.add(organizationClass);
                }
                listView.setAdapter(organizationCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab_id){
            Intent intent = new Intent(OrganizationActivity.this,AddOrganizatoinActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.organization_page_menu_layout,menu);

        this.menu = menu;
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_selectLocationForOrganization_id){
            Toast.makeText(this, "clicked!!", Toast.LENGTH_SHORT).show();

            Intent intent =new Intent(OrganizationActivity.this,districtList_For_OrganizationSearch.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(!final_district_selection.equals("") && exceptionFlag!=1){
            MenuItem menuItem = menu.findItem(R.id.action_selectLocationForOrganization_id);

            menuItem.setTitle(final_district_selection);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateMenuTitles(String menuName){
        MenuItem menuItem = menu.findItem(R.id.action_selectLocation_id);

        menuItem.setTitle(menuName);
    }

}
