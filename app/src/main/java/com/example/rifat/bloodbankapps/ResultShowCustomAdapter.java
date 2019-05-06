package com.example.rifat.bloodbankapps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ResultShowCustomAdapter extends ArrayAdapter<DonorClass> implements View.OnClickListener {

    private ListView listView;

    private Activity context;
    private List<DonorClass> donorList;

    public static final int REQUEST_CALL=1;
    public String callNumber;


    StringBuilder sb = new StringBuilder();

    //ArrayList<String> pn = new ArrayList<String>();
    public int index=0;

    public ResultShowCustomAdapter(Activity context, List<DonorClass> donorList) {
        super(context, R.layout.search_donor_result_sample_layout,donorList);
        this.context = context;
        this.donorList = donorList;


        // my test.......................

    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.search_donor_result_sample_layout,null,true);

        DonorClass donorClass = donorList.get(position);

       // listView=(ListView)view.findViewById(R.id.searchBloodResultShowListView_id);

        TextView Name=view.findViewById(R.id.donorNameTextViwsample_id);
        TextView BloodGroup=view.findViewById(R.id.donorBloodGroupTextViwsample_id);
        TextView District=view.findViewById(R.id.donorDistrictTextViwsample_id);

       /* TextView PhoneNumber=view.findViewById(R.id.donorPhoneNumberTextViwsample_id);
        TextView EmailAddress=view.findViewById(R.id.donorEmailTextViwsample_id);
        TextView Gender=view.findViewById(R.id.donorGendersample_id);*/


        Name.setText("Name: "+donorClass.getDonor_name());
        BloodGroup.setText("Blood Group: "+donorClass.getDonor_bloodGroup());
        District.setText("District: "+donorClass.getDonor_district());
        /*PhoneNumber.setText("Phone: +88"+donorClass.getDonor_phone_number());
        EmailAddress.setText("Email: "+donorClass.getDonor_email());
        Gender.setText("Gender: "+donorClass.getDonor_gender());   */


        //sb.append(donorClass.getDonor_phone_number());
        //sb.append("\n");



        //Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();



        //callNumber=PhoneNumber.getText().toString().trim();
        callNumber=donorClass.getDonor_phoneNumber();

        /*ImageView PhoneIcon=view.findViewById(R.id.donorPhoneIcon_id);
        ImageView EmailIcon=view.findViewById(R.id.donorEmailIcon_id);

        PhoneIcon.setOnClickListener(this);
        EmailIcon.setOnClickListener(this);
*/
        return view;
    }

    @Override
    public void onClick(View v) {

       /*if(v.getId()==R.id.donorPhoneIcon_id){




       }*/
    }

    private void makeCall() {

        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dial="tel:"+callNumber;
            context.startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }

// others function..........................

    public void  onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults)
    {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeCall();
            }else{
                Toast.makeText(context,"Permission Denied!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
