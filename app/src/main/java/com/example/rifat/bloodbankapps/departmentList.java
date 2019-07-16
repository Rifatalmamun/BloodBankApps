package com.example.rifat.bloodbankapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class departmentList extends AppCompatActivity {

    private SearchView searchView;

    private ListView listView;
    private String[] departmentNameArray;
    private String pos;

    public String catchName,catchBloodGroup,catchPhone,catchDist,catchSess,catchLastDate,catchkeY,catchRanD,catchParentBG,catchParentDis,catchParentPHN,catchPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        try{
            catchName=getIntent().getExtras().getString("sendName");
            catchBloodGroup=getIntent().getExtras().getString("sendBloodGroup");
            catchPhone=getIntent().getExtras().getString("sendPhone");
            catchDist=getIntent().getExtras().getString("sendDist");
            catchSess=getIntent().getExtras().getString("sendSession");
            catchLastDate=getIntent().getExtras().getString("sendLastDate");
            catchkeY=getIntent().getExtras().getString("sendKEY");
            catchRanD=getIntent().getExtras().getString("sendRANDOM");
            catchParentBG=getIntent().getExtras().getString("sendPARENTGROUP");
            catchParentDis=getIntent().getExtras().getString("sendPARENTDISTRICT");
            catchParentPHN=getIntent().getExtras().getString("sendPARENTPHONENUMBER");
            catchPassword=getIntent().getExtras().getString("sendPASSWORD");

            if(catchBloodGroup.equals("A+")){
                pos = "0";
            }else if(catchBloodGroup.equals("O+")){
                pos = "1";
            }else if(catchBloodGroup.equals("AB+")){
                pos = "2";
            }else if(catchBloodGroup.equals("B+")){
                pos = "3";
            }else if(catchBloodGroup.equals("A-")){
                pos = "4";
            }else if(catchBloodGroup.equals("B-")){
                pos = "5";
            }else if(catchBloodGroup.equals("AB-")){
                pos = "6";
            }else if(catchBloodGroup.equals("O-")){
                pos = "7";
            }
        }catch(Exception e){

        }

        searchView=(SearchView)findViewById(R.id.searchView_id);
        listView=(ListView)findViewById(R.id.departmentListView_id);
        departmentNameArray=getResources().getStringArray(R.array.DepartmentArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(departmentList.this,R.layout.departmentlistsmaplelayout,R.id.departmentlistSampleTextView_id,departmentNameArray);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = adapter.getItem(position);

                if(!value.isEmpty()){

                    Intent intent = new Intent(departmentList.this,ProfileEditActivity.class);
                    intent.putExtra("selectedCity",catchDist);
                    intent.putExtra("UN",catchName);
                    intent.putExtra("BG",catchBloodGroup);
                    intent.putExtra("P",pos);
                    intent.putExtra("PN",catchPhone);
                    intent.putExtra("DEPT",value);
                    intent.putExtra("SESS",catchSess);
                    intent.putExtra("LAST",catchLastDate);
                    intent.putExtra("KEY",catchkeY);
                    intent.putExtra("RANDOM",catchRanD);
                    intent.putExtra("PARENTGROUP",catchParentBG);
                    intent.putExtra("PARENTDISTRICT",catchParentDis);
                    intent.putExtra("PARENTPHONENUMBER",catchParentPHN);
                    intent.putExtra("PASSWORD",catchPassword);

                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}
