package com.example.rifat.bloodbankapps;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.List;

public class ResultShowCustomAdapter extends ArrayAdapter<DonorClass> {

    private Activity context;
    private List<DonorClass> donorList;

    public ResultShowCustomAdapter(Activity context, List<DonorClass> donorList) {
        super(context, R.layout.search_donor_result_sample_layout,donorList);
        this.context = context;
        this.donorList = donorList;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.search_donor_result_sample_layout,null,true);

        DonorClass donorClass = donorList.get(position);

        TextView Name=view.findViewById(R.id.donorNameTextViwsample_id);
        TextView BloodGroup=view.findViewById(R.id.donorBloodGroupTextViwsample_id);
        TextView District=view.findViewById(R.id.donorDistrictTextViwsample_id);

        TextView PhoneNumber=view.findViewById(R.id.donorPhoneNumberTextViwsample_id);
        TextView EmailAddress=view.findViewById(R.id.donorEmailTextViwsample_id);
        TextView Gender=view.findViewById(R.id.donorGendersample_id);



        Name.setText("Name: "+donorClass.getDonor_name());
        BloodGroup.setText("Blood Group: "+donorClass.getDonor_blood_group());
        District.setText("District: "+donorClass.getDonor_district());
        PhoneNumber.setText("Phone: "+donorClass.getDonor_phone_number());
        EmailAddress.setText("Email: "+donorClass.getDonor_email());
        Gender.setText("Gender: "+donorClass.getDonor_gender());



        return view;
    }
}
