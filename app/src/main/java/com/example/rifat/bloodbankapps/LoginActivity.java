package com.example.rifat.bloodbankapps;

import android.content.Intent;
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

    private EditText phoneNumber;
    private Button loginButton;

    private List<DonorClass> donorProfileList;

    private String chk,findPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber=(EditText)findViewById(R.id.loginActivityPhoneNumber_id);
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
                }

                if(chk==null){
                    Toast.makeText(LoginActivity.this, "Fail !! ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "chk: "+chk, Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.checkPoint="finish";
                    AddDonorActivity.appsUserMobileNumber=findPhone;

                    Toast.makeText(LoginActivity.this, "Login Successfull !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
