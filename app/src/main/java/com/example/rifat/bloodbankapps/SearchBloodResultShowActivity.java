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

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
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
    StringBuilder checkDonationDate=new StringBuilder();
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

                    String s = donorClass.getDonor_lastDonationDate();

                    if(s.equals("I don't know")){

                        stringBuilder.append(donorClass.getDonor_phoneNumber());
                        donorList.add(donorClass);

                    }
                    if(!s.equals("I don't know")){

                        String dDay=s.substring(0,2);
                        String dMonth=s.substring(3,5);
                        String dYear=s.substring(6,10);

                        // donation date in integer............

                        int dd = Integer.parseInt(dDay);
                        int dm = Integer.parseInt(dMonth);
                        int dy = Integer.parseInt(dYear);

                        // current date in integer..............

                        Calendar c =Calendar.getInstance();
                        int cd=c.get(Calendar.DAY_OF_MONTH);
                        int cm=c.get(Calendar.MONTH)+1;
                        int cy=c.get(Calendar.YEAR);

                        // calculation start................

                        int month_diff=cm-dm;
                        int year_diff=cy-dy;

                        if(month_diff<0){
                            year_diff = year_diff - 1;
                            month_diff = month_diff + 12;
                        }
                        int day_diff = cd - dd;
                        if(day_diff<0){
                            if(month_diff>0){
                                month_diff = month_diff-1;
                                day_diff = day_diff +MonthsToDays(cm-1,cy);
                            }else{
                                year_diff = year_diff - 1;
                                month_diff = 11;
                                day_diff = day_diff+MonthsToDays(cm-1,cy);
                            }
                        }
                        int TD=day_diff+(month_diff*30)+(year_diff*365);

                        if(TD>=30){
                            stringBuilder.append(donorClass.getDonor_phoneNumber());
                            donorList.add(donorClass);
                        }
                        Toast.makeText(SearchBloodResultShowActivity.this, "gap: "+TD, Toast.LENGTH_LONG).show();
                    }
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

               // Toast.makeText(getApplicationContext(),"Ref: "+ref,Toast.LENGTH_SHORT).toString();

                startActivity(intent);


                //Toast.makeText(SearchBloodResultShowActivity.this, "Pos: "+position, Toast.LENGTH_SHORT).show();

            }
        });

    }
// method to find dayofmonth for leapyear method.............................
    public static int MonthsToDays(int tMonth, int tYear) {
        if (tMonth == 1 || tMonth == 3 || tMonth == 5 || tMonth == 7
                || tMonth == 8 || tMonth == 10 || tMonth == 12) {
            return 31;
        } else if (tMonth == 2) {
            if (tYear % 4 == 0) {
                return 29;
            } else {
                return 28;
            }
        } else {
            return 30;
        }
    }
}