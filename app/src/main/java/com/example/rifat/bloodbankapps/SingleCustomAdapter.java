package com.example.rifat.bloodbankapps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.rifat.bloodbankapps.ResultShowCustomAdapter.REQUEST_CALL;

public class SingleCustomAdapter extends ArrayAdapter<DonorClass> implements View.OnClickListener {

    private ListView listView;

    private Activity context;
    private List<DonorClass> donorList;

    public static final int REQUEST_CALL=1;
    public static final int SEND_SMS_REQUEST_PERMISSION_CODE=1;
    public String callNumber;

    public SingleCustomAdapter(Activity context, List<DonorClass> donorList) {
        super(context, R.layout.single_result_sample_layout,donorList);
        this.context = context;
        this.donorList = donorList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.single_result_sample_layout,null,true);

        DonorClass donorClass = donorList.get(position);


        TextView Name=view.findViewById(R.id.singleDonorNameTextViwsample_id);
        TextView BloodGroup=view.findViewById(R.id.singleDonorBloodGroupTextViwsample_id);
        TextView District=view.findViewById(R.id.singleDonorDistrictTextViwsample_id);

        TextView PhoneNumber=view.findViewById(R.id.singleDonorPhoneNumberTextViwsample_id);
        //TextView EmailAddress=view.findViewById(R.id.singleDonorEmailTextViwsample_id);
        //TextView Gender=view.findViewById(R.id.singleDonorGendersample_id);

        TextView Department=view.findViewById(R.id.singleDonorDepartmentsample_id);
        TextView Session=view.findViewById(R.id.singleDonorSessionsample_id);
        TextView LastDonationDate=view.findViewById(R.id.singleDonorLastDonationsample_id);



        Name.setText("Name: "+donorClass.getDonor_name());
        BloodGroup.setText("Blood Group: "+donorClass.getDonor_bloodGroup());
        District.setText("District: "+donorClass.getDonor_district());
        PhoneNumber.setText("Phone: +88"+donorClass.getDonor_phoneNumber());
       // EmailAddress.setText("Email: "+donorClass.getDonor_email());
        //Gender.setText("Gender: "+donorClass.getDonor_gender());

        if(!donorClass.getDonor_department().equals("")){
            Department.setVisibility(View.VISIBLE);
        }
        if(!donorClass.getDonor_session().equals("")){
            Session.setVisibility(View.VISIBLE);
        }

        Department.setText("Departement: "+donorClass.getDonor_department());
        Session.setText("Session: "+donorClass.getDonor_session());
        LastDonationDate.setText("Last Donation Date: "+donorClass.getDonor_lastDonationDate());

        callNumber=donorClass.getDonor_phoneNumber();

        ImageView PhoneIcon=view.findViewById(R.id.singleDonorPhoneIcon_id);
        ImageView EmailIcon=view.findViewById(R.id.singleDonorEmailIcon_id);

        PhoneIcon.setOnClickListener(this);
        EmailIcon.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.singleDonorPhoneIcon_id){
            alertMethod("Are you really want to call?","1");
        }
        if(v.getId()==R.id.singleDonorEmailIcon_id){
            alertMethod("Are you really want to sms?","2");
        }
    }

    private void alertMethod(String mess, final String flag) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(mess);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(flag.equals("1")){
                            makeCall();
                        }
                        if(flag.equals("2")){
                            smsService();
                        }
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    public void makeCall()
    {
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

    // sms method...............................

    public void smsService()
    {
        int permissionCheck=ContextCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {


            String message="Need blood, A+ blood ";

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(callNumber,null,message,null,null);
            Toast.makeText(context, "Sms Sent Successfully", Toast.LENGTH_SHORT).show();

        }else{
            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

}
