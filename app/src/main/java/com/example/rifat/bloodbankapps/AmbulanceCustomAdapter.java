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

public class AmbulanceCustomAdapter extends ArrayAdapter<AmbulanceClass> {

    private ListView listView;
    private Activity context;
    private List<AmbulanceClass> ambulanceList;

    public AmbulanceCustomAdapter (Activity context, List<AmbulanceClass> ambulanceList) {
        super(context, R.layout.bloodbank_listview_sample_layout, ambulanceList);
        this.context = context;
        this.ambulanceList = ambulanceList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.bloodbank_listview_sample_layout,null,true);
        AmbulanceClass ambulanceClass = ambulanceList.get(position);

        TextView AmbulanceName=view.findViewById(R.id.bloodBankSampleOrganization_id);
        TextView Address=view.findViewById(R.id.bloodBankSampleAddress_id);
        //TextView Open=view.findViewById(R.id.bloodBankSampleOpen_id);
        TextView Phone=view.findViewById(R.id.bloodBankSamplePhone_id);

        // ImageView PhoneImg=view.findViewById(R.id.bloodBankSamplePhoneImg_id);

        AmbulanceName.setText("Ambulance name: "+ambulanceClass.getOrganizationName());
        Address.setText("Phone: "+ambulanceClass.getAddress());
        //Open.setText(bloodBankClass.getOpen());
        Phone.setText("Location: "+ambulanceClass.getPhone());



        return view;
    }
}
