package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements View.OnClickListener {

    private Menu menu;

    private ListView listView;
    private List<BloodBankClass> bloodBankList;
    private BloodBankCustomAdapter bloodBankCustomAdapter;


    private ImageView districtSpinner;
    private TextView districtTextView;
    private String[] districtArrayBloodBank;

    private EditText bloodbankName,bloodBankLocation,bloodBankPhonenumber;

    private String final_district_selection="";
    private int exceptionFlag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        this.setTitle("Blood Bank");

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

        //..................................................................

        listView=(ListView)findViewById(R.id.bloodBankListView_id);
        bloodBankList=new ArrayList<>();
        bloodBankCustomAdapter = new BloodBankCustomAdapter(BloodBankActivity.this,bloodBankList);

        final DatabaseReference myRefBB=FirebaseDatabase.getInstance().getReference("BloodBankTable");

        myRefBB.child(final_district_selection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
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
        if(v.getId()==R.id.fab_id){
            Intent intent = new Intent(BloodBankActivity.this,AddBloodBankActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bank_page_menu_layout,menu);

       /* if(!final_district_selection.isEmpty()){
            updateMenuTitles(final_district_selection);
        }else{
            updateMenuTitles("Jessore");
        }*/

        this.menu = menu;
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_selectLocation_id){
            Toast.makeText(this, "clicked!!", Toast.LENGTH_SHORT).show();

            Intent intent =new Intent(BloodBankActivity.this,districtList_For_BloodBankSearch.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(!final_district_selection.equals("") && exceptionFlag!=1){
            MenuItem menuItem = menu.findItem(R.id.action_selectLocation_id);

            menuItem.setTitle(final_district_selection);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateMenuTitles(String menuName){
        MenuItem menuItem = menu.findItem(R.id.action_selectLocation_id);

        menuItem.setTitle(menuName);
    }
}
