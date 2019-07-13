package com.example.rifat.bloodbankapps;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FactsCustomAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<String> listDateHeader;
    HashMap<String,List<String>> listDateChild;



    // Create Constructor
    public FactsCustomAdapter(Context context, List<String> listDateHeader, HashMap<String, List<String>> listDateChild) {
        this.context = context;
        this.listDateHeader = listDateHeader;
        this.listDateChild = listDateChild;
    }

    @Override
    public int getGroupCount() {
        return listDateHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listDateChild.get(listDateHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDateHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listDateChild.get(listDateHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        String headerText=(String)getGroup(groupPosition);

        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.facts_question_layout,null);
        }

        TextView textView =view.findViewById(R.id.questionTextView_id);
        //Typeface typeface=Typeface.createFromAsset(getAssets(),"font/kalpurush.ttf");
        textView.setText(headerText);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        String childText=(String)getChild(groupPosition,childPosition);

        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.facts_answer_layout,null);
        }

        TextView textView =view.findViewById(R.id.answerTextView_id);
        textView.setText(childText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
