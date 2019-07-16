package com.example.rifat.bloodbankapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNumber,passwordNumber;
    private Button loginButton;

    private List<DonorClass> donorProfileList;

    private String chk,findPhone,findPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber=(EditText)findViewById(R.id.loginActivityPhoneNumber_id);
        passwordNumber=(EditText)findViewById(R.id.loginActivityPassword_id);
        loginButton=(Button)findViewById(R.id.login_id);

        loginButton.setOnClickListener(this);

        donorProfileList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_id){
            String logPhone= phoneNumber.getText().toString();
            loadDate(logPhone);
        }
    }

    private void loadDate(String logPhone) {


        final DatabaseReference profileRefForFirstPage = FirebaseDatabase.getInstance().getReference("MyProfileTable");

        profileRefForFirstPage.child(logPhone).orderByChild("donor_phoneNumber")
                .equalTo(logPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorProfileList.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    DonorClass donorClass = dataSnapshot1.getValue(DonorClass.class);

                    chk=donorClass.getDonor_bloodGroup();
                    findPhone=donorClass.getDonor_phoneNumber();
                    findPassword=donorClass.getDonor_password();
                }

                if(chk==null){
                    Toast.makeText(LoginActivity.this, "Fail to login!! ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "chk: "+chk, Toast.LENGTH_SHORT).show();
                }if(passwordNumber.getText().toString().equals(findPassword)){
                    loginInformationSaveInSharedPreference(findPhone,findPassword);
                    MainActivity.checkPoint="finish";
                    AddDonorActivity.appsUserMobileNumber=findPhone;
                    AddDonorActivity.appsUserPasswordNumber=findPassword;

                    Toast.makeText(LoginActivity.this, "Login Successfull !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "password or phone number wrong!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void loginInformationSaveInSharedPreference(String phone,String password) {
        AddDonorActivity.appsUserMobileNumber=phone;
        AddDonorActivity.appsUserPasswordNumber=password;

        SharedPreferences  sharedPreferences=getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userLoginPhoneNumber",phone);
        editor.putString("userLoginPasswordNumber",password);

        editor.commit();
        Toast.makeText(LoginActivity.this,"login info stored Successfully ",Toast.LENGTH_SHORT).show();
    }
}