package com.example.rifat.bloodbankapps;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class OrganizationCustomAdapter extends ArrayAdapter<OrganizationClass> {

    private ListView listView;
    private Activity context;
    private List<OrganizationClass> organizationList;

    public OrganizationCustomAdapter (Activity context, List<OrganizationClass> organizationList) {
        super(context, R.layout.bloodbank_listview_sample_layout, organizationList);
        this.context = context;
        this.organizationList = organizationList;
    }

        @NonNull
        @Override
        public View getView(int position,View convertView,ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();

            View view = layoutInflater.inflate(R.layout.bloodbank_listview_sample_layout,null,true);
            OrganizationClass organizationClass = organizationList.get(position);

            TextView OrganizationName=view.findViewById(R.id.bloodBankSampleOrganization_id);
            TextView Address=view.findViewById(R.id.bloodBankSampleAddress_id);
            //TextView Open=view.findViewById(R.id.bloodBankSampleOpen_id);
            TextView Phone=view.findViewById(R.id.bloodBankSamplePhone_id);

            // ImageView PhoneImg=view.findViewById(R.id.bloodBankSamplePhoneImg_id);

            OrganizationName.setText("Organization: "+organizationClass.getOrganizationName());
            Address.setText("Phone: "+organizationClass.getAddress());
            //Open.setText(bloodBankClass.getOpen());
            Phone.setText("Location: "+organizationClass.getPhone());


            return view;
        }
}
