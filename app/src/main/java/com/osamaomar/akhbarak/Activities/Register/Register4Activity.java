package com.osamaomar.akhbarak.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.osamaomar.akhbarak.R;

public class Register4Activity extends AppCompatActivity {
    EditText password;
    TextView next,back,name,city,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);

        final String names=getIntent().getStringExtra("name");
        final String citys=getIntent().getStringExtra("city");
        final String mails=getIntent().getStringExtra("mail");

        mail =findViewById(R.id.mail);
        next =findViewById(R.id.next);
        back =findViewById(R.id.back);
        name =findViewById(R.id.name);
        city =findViewById(R.id.city);
        password =findViewById(R.id.password);

        name.setText(names);
        city.setText(citys);
        mail.setText(mails);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (password.getText().toString().matches(""))
                    password.setError("أدخل كلمة المرور");

               else {
                   Intent intent = new Intent(Register4Activity.this, Register5Activity.class);
                   intent.putExtra("name", names);
                   intent.putExtra("city", citys);
                   intent.putExtra("mail", mails);
                   intent.putExtra("pass", password.getText().toString());
                   startActivity(intent);
               }

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

