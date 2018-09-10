package com.osamaomar.akhbarak.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.osamaomar.akhbarak.R;

public class Register3Activity extends AppCompatActivity {

    EditText mail;
    TextView next,back,name,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        final String names=getIntent().getStringExtra("name");
        final String citys=getIntent().getStringExtra("city");
         mail =findViewById(R.id.mail);
        next =findViewById(R.id.next);
        back =findViewById(R.id.back);
        name =findViewById(R.id.name);
        city =findViewById(R.id.city);

        name.setText(names);
        city.setText(citys);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (mail.getText().toString().matches(""))

                Intent intent=new Intent(Register3Activity.this,Register4Activity.class);
                intent.putExtra("name",names);
                intent.putExtra("city",citys);
                intent.putExtra("mail",mail.getText().toString());
                startActivity(intent);


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
