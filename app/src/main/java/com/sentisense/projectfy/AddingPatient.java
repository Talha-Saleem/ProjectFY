package com.sentisense.projectfy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sentisense.projectfy.Model.AddPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingPatient extends AppCompatActivity {
    EditText id,name,phone,add,ag;
    Button save;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        name=findViewById(R.id.name);
        phone =findViewById(R.id.phno);
        add=findViewById(R.id.add);
        ag=findViewById(R.id.age);
        save=findViewById(R.id.save);

        //mAuth = FirebaseAuth.getInstance();



        //Toast.makeText(AddingPatient.this, username, Toast.LENGTH_SHORT).show();

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //assert user != null;
//        String userid = user.getUid();




            databaseReference= FirebaseDatabase.getInstance().getReference("AddedPatients");
            save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final String txt_username=name.getText().toString();
                    final String txt_phno=phone.getText().toString();
                    final String txt_address=add.getText().toString();
                    final String txt_age=ag.getText().toString();

                    if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_phno) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_age)) {
                        Toast.makeText(AddingPatient.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        //assert firebaseUser != null;
                        //String id = firebaseUser.getUid();



                        //String useremail=emailid.replace(".",",");

                        String i=getIntent().getStringExtra("doc");
                        String userid = databaseReference.push().getKey();
                        AddPatient patient = new AddPatient();

                        patient.setId(userid);
                        patient.setUsername(txt_username);
                        patient.setPhno(txt_phno);
                        patient.setAddress(txt_address);
                        patient.setAge(txt_age);
                        patient.setDocid(i);

                        databaseReference.child(userid).setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(AddingPatient.this, PatientList.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Toast.makeText(AddingPatient.this, "Success", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(AddingPatient.this, username, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                } else {
                                    //display a failure message
                                }
                            }
                        });
                    }
                }
            });



    }

}
