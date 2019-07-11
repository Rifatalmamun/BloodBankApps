package com.example.rifat.bloodbankapps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private Menu menu;

    ActionBar actionBar;
    //Declaring all variables name...........................................

    private TextView userNumber,donorNumber;
    private CardView beDonor,searchBlood,bloodBank,ambulance,addDonor,facts,about,shareApp;

    public static String checkPoint="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            checkPoint=getIntent().getExtras().getString("checkPoint");
        }catch(Exception e){

        }

        BeDonorActivity.flage=0;

        // internet connection check;
        if(!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
        }

        this.setTitle("JUST Blood Bank");

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

        //userNumber=(TextView)findViewById(R.id.userNumberTextView_id);
        donorNumber=(TextView)findViewById(R.id.donorNumberTextView_id);

       // beDonor=(CardView)findViewById(R.id.beADonor_id);
        searchBlood=(CardView)findViewById(R.id.searchBlood_id);
        bloodBank=(CardView)findViewById(R.id.bloodBank_id);
        ambulance=(CardView)findViewById(R.id.ambulance_id);
        addDonor=(CardView)findViewById(R.id.addDonor_id);
        facts=(CardView)findViewById(R.id.facts_id);
        about=(CardView)findViewById(R.id.about_id);
        shareApp=(CardView)findViewById(R.id.share_id);

        // set OnClick Listener.............................

        //beDonor.setOnClickListener(this);
        searchBlood.setOnClickListener(this);
        bloodBank.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        addDonor.setOnClickListener(this);
        facts.setOnClickListener(this);
        about.setOnClickListener(this);
        shareApp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        /*if(v.getId()==R.id.beADonor_id){

            Intent intent = new Intent(MainActivity.this,BeDonorActivity.class);
            startActivity(intent);

        }*/ if(v.getId()==R.id.searchBlood_id){

            Intent intent = new Intent(MainActivity.this,SearchBloodActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.bloodBank_id){

            Intent intent = new Intent(MainActivity.this,BloodBankActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.ambulance_id){

            Intent intent = new Intent(MainActivity.this,AmbulanceActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.addDonor_id){
            try{
               if(checkPoint.equals("finish")){
                   Intent intent = new Intent(MainActivity.this,AddDonorActivity.class);
                   startActivity(intent);
                }else{
                   Toast.makeText(this, "Please sign in  to use the feature!", Toast.LENGTH_SHORT).show();
               }

            }catch (Exception e){
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.facts_id){

            Intent intent = new Intent(MainActivity.this,FactsActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.about_id){

            Intent intent = new Intent(MainActivity.this,currentLocation.class);
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

        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_signIn_id) {

            Intent intent = new Intent(MainActivity.this,BeDonorActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_signIn_id);
        if(checkPoint.equals("finish")){
            menuItem.setTitle("");
            menuItem.setVisible(false);
            this.invalidateOptionsMenu();
        }else{
            menuItem.setTitle("Sing_up");
        }

        return super.onPrepareOptionsMenu(menu);
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
//.................................... net conected check............................................

    public boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if(netinfo !=null && netinfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile !=null && mobile.isConnectedOrConnecting()) || (wifi !=null && wifi.isConnectedOrConnecting()) ){
                return true;
            }else{
                return false;
            }
        }else
            return false;
    }

   // alert dialog.....................
    public AlertDialog.Builder buildDialog(Context c)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection!");
        builder.setMessage("You need to connect your device with internet ........");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        return builder;
    }


}