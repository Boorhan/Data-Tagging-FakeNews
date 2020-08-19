package com.example.tempp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView headline,content;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btnSubmit,btnFake,btnNotfake;
    EditText editTextComment;
    int temp=0;
    String btnfakeOrnot="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headline = findViewById(R.id.headline);
        content = findViewById(R.id.textContent);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnFake = (Button) findViewById(R.id.btnFake);
        btnNotfake = (Button) findViewById(R.id.btnNotfake);
        editTextComment = (EditText) findViewById(R.id.editText);
        database = FirebaseDatabase.getInstance();
        //btn = findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //loadNext();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFake.setBackgroundColor(Color.GREEN);
                btnNotfake.setBackgroundColor(Color.GRAY);
                btnfakeOrnot = "1";
                Log.i("custom","butn");
            }
        });

        // is not fake it is zero.
        btnNotfake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNotfake.setBackgroundColor(Color.GREEN);
                btnFake.setBackgroundColor(Color.GRAY);
                btnfakeOrnot = "0";
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnfakeOrnot != "null"){
                    String comment =editTextComment.getText().toString();
                    if(comment.length()>5){

                        String SS= loadNext();

                        doTask(btnfakeOrnot, comment, SS);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please explain why>", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Please select Fake or not", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void doTask(String fake, String comment, String index){
        database = FirebaseDatabase.getInstance();
        //String s = Integer.toString(temp);
        myRef = database.getReference(index);


        myRef.child("fake").setValue(fake);
        myRef.child("comment").setValue(comment);
        myRef.child("check").setValue("true");

        myRef.child("content").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                content.setText(value);
                //Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                headline.setText(value);
                //Toast.makeText(MainActivity.this, "dhabjdskjasfkjdakjs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public String nextCheck() {
        Log.i("custom", "next check");
        String current="";
        boolean flag = false;
        for(int i=0; i<4743; i++){
            current = Integer.toString(i);
            Log.i("custom" ,current);
            myRef = database.getReference(current);
            myRef.child("check").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Log.i("custom" ,"Value is: " + value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        return current;

    }

    public String loadNext(){
        Log.i("custom","loadnext");
        String next = nextCheck();
        myRef = database.getReference(next);

        myRef.child("content").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                content.setText(value);
                //Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                headline.setText(value);
                //Toast.makeText(MainActivity.this, "dhabjdskjasfkjdakjs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnNotfake.setBackgroundColor(Color.BLUE);
        btnFake.setBackgroundColor(Color.BLUE);

        return next;


    }


}