package com.example.rifat.bloodbankapps;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactsActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;

    private int lastExpandedPosition=-1;

    List<String> listDateHeader;
    HashMap<String,List<String>> listDateChild;

    private FactsCustomAdapter factsCustomAdapter;


    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        this.setTitle("Facts");




        expandableListView=(ExpandableListView) findViewById(R.id.expandabelListView_id);
        expandableListView.setGroupIndicator(null);
        prepareListData();
        factsCustomAdapter=new FactsCustomAdapter(this,listDateHeader,listDateChild);
        expandableListView.setAdapter(factsCustomAdapter);

        // for adding listener.......when open Expanded Header list
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                String groupName= listDateHeader.get(groupPosition);
                //Toast.makeText(getApplicationContext(),groupName+" open",Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // when collaps expandent Header list
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                String groupName=listDateHeader.get(groupPosition);
                //Toast.makeText(getApplicationContext(),groupName+" off",Toast.LENGTH_LONG).show();
            }
        });
        // when click child list
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String childName=listDateChild.get(listDateHeader.get(groupPosition)).get(childPosition);
               // Toast.makeText(getApplicationContext(),childName,Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // ager open kora Expanded list ta close hobe ei listener dara
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition!=-1 && lastExpandedPosition!=groupPosition){
                    // for collapse previous
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition=groupPosition;
            }
        });


    }


    public void prepareListData()
    {
        String[] header_String=getResources().getStringArray(R.array.FactsQuestion);
        String[] child_String=getResources().getStringArray(R.array.FactsAnswer);

        listDateHeader = new ArrayList<>();
        listDateChild = new HashMap<>();

        for(int i=0;i<header_String.length;i++)
        {
            // adding header_String and listDataHeader;
            listDateHeader.add(header_String[i]);
            // for child
            List<String> child=new ArrayList<>();
            child.add(child_String[i]);

            listDateChild.put(listDateHeader.get(i),child);
        }
    }
}
