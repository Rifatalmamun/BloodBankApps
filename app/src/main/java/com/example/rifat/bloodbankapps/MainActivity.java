package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    ActionBar actionBar;
    //Declaring all variables name...........................................

    private TextView userNumber,donorNumber;
    private CardView beDonor,searchBlood,bloodBank,ambulance,postForBlood,facts,about,shareApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Project OnCreate Method starts Here......................................................
        // Find all variable........................................................................

        userNumber=(TextView)findViewById(R.id.userNumberTextView_id);
        donorNumber=(TextView)findViewById(R.id.donorNumberTextView_id);

        beDonor=(CardView)findViewById(R.id.beADonor_id);
        searchBlood=(CardView)findViewById(R.id.searchBlood_id);
        bloodBank=(CardView)findViewById(R.id.bloodBank_id);
        ambulance=(CardView)findViewById(R.id.ambulance_id);
        postForBlood=(CardView)findViewById(R.id.postForBlood_id);
        facts=(CardView)findViewById(R.id.facts_id);
        about=(CardView)findViewById(R.id.about_id);
        shareApp=(CardView)findViewById(R.id.share_id);

        // set OnClick Listener.............................

        beDonor.setOnClickListener(this);
        searchBlood.setOnClickListener(this);
        bloodBank.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        postForBlood.setOnClickListener(this);
        facts.setOnClickListener(this);
        about.setOnClickListener(this);
        shareApp.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.beADonor_id){

            Intent intent = new Intent(MainActivity.this,BeDonorActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.searchBlood_id){

            Intent intent = new Intent(MainActivity.this,SearchBloodActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.bloodBank_id){

            Intent intent = new Intent(MainActivity.this,BloodBankActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.ambulance_id){

            Intent intent = new Intent(MainActivity.this,AmbulanceActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.postForBlood_id){

            Intent intent = new Intent(MainActivity.this,PostForBloodActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.facts_id){

            Intent intent = new Intent(MainActivity.this,FactsActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.about_id){

            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.share_id){

            Intent intent = new Intent(MainActivity.this,ShareActivity.class);
            startActivity(intent);

        }
    }














































    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_id) {

        } else if (id == R.id.nav_share_id) {


        } else if (id == R.id.nav_update_id) {

        } else if (id == R.id.nav_othres_id) {

        }else if (id == R.id.nav_rating_id) {

        } else if (id == R.id.nav_exit_id) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
