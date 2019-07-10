package com.example.rifat.bloodbankapps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExampleDialog extends  AppCompatDialogFragment implements View.OnClickListener {

    private EditText bloodBankNameEditText,bloodBankPhoneEditText;
    private TextView bloodBankDistrictTextView;
    private ImageView bloodBankDistrictSpinner;
    private ExampleDialogListener listener;

    private String nameFromDistrictList="";
    private String numberFromDistrictList="";
    private String districtFromDistrictList="";





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.alert_dialog_sample_layout,null);



        builder.setView(view)
                .setTitle("Add Blood Bank Information")
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String takeBloodBankName=bloodBankNameEditText.getText().toString();
                        String takeBloodBankPhone=bloodBankPhoneEditText.getText().toString();

                        listener.applyTexts(takeBloodBankName,takeBloodBankPhone);
                    }
                });
        bloodBankNameEditText=view.findViewById(R.id.alertDialogBloodBankNameEditText_id);
        bloodBankPhoneEditText=view.findViewById(R.id.alertDialogBloodBankPhoneEditText_id);
        bloodBankDistrictTextView=view.findViewById(R.id.alertDialogBloodBankLocationTextView_id);
        bloodBankDistrictSpinner=view.findViewById(R.id.alertDialogBloodBankSpinner_id);

        bloodBankDistrictSpinner.setOnClickListener(this);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener=(ExampleDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alertDialogBloodBankSpinner_id){
            //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(getContext(),currentLocation.class);

            String takeBBName =  bloodBankNameEditText.getText().toString();
            String takeBBNumber =  bloodBankPhoneEditText.getText().toString();

            intent.putExtra("bbName",takeBBName);
            intent.putExtra("bbNumber",takeBBNumber);

            startActivity(intent);
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String name,String phone);
    }
}
