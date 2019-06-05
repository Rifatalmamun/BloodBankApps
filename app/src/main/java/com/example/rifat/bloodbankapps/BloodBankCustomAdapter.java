package com.example.rifat.bloodbankapps;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BloodBankCustomAdapter extends ArrayAdapter<BloodBankClass> {

    private ListView listView;
    private Activity context;
    private List<BloodBankClass> bloodBankList;

    public BloodBankCustomAdapter (Activity context, List<BloodBankClass> bloodBankList){
        super(context, R.layout.bloodbank_listview_sample_layout,bloodBankList);
        this.context = context;
        this.bloodBankList = bloodBankList;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.bloodbank_listview_sample_layout,null,true);

        BloodBankClass bloodBankClass = bloodBankList.get(position);

        TextView OrganizationName=view.findViewById(R.id.bloodBankSampleOrganization_id);
        TextView Address=view.findViewById(R.id.bloodBankSampleAddress_id);
        TextView Open=view.findViewById(R.id.bloodBankSampleOpen_id);
        TextView Phone=view.findViewById(R.id.bloodBankSamplePhone_id);

        ImageView PhoneImg=view.findViewById(R.id.bloodBankSamplePhoneImg_id);

        OrganizationName.setText(bloodBankClass.getOrganizationName());
        Address.setText(bloodBankClass.getAddress());
        Open.setText(bloodBankClass.getOpen());
        Phone.setText(bloodBankClass.getPhone());


        return view;
    }
}
