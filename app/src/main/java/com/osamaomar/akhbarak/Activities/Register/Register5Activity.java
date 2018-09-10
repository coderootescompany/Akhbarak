package com.osamaomar.akhbarak.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.osamaomar.akhbarak.R;

public class Register5Activity extends AppCompatActivity {
    EditText repassword;
    TextView next,back,name,city,mail,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);

        final String names=getIntent().getStringExtra("name");
        final String citys=getIntent().getStringExtra("city");
        final String mails=getIntent().getStringExtra("mail");
        final String pass=getIntent().getStringExtra("pass");

        mail =findViewById(R.id.mail);
        next =findViewById(R.id.next);
        back =findViewById(R.id.back);
        name =findViewById(R.id.name);
        city =findViewById(R.id.city);
        password =findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        name.setText(names);
        city.setText(citys);
        mail.setText(mails);
        password.setText(pass);


        StringBuilder pass2= new StringBuilder();
        for (int i=0;i<pass.length();i++)
            pass2.append("*");

        password.setText(pass2.toString());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repassword.getText().toString().matches(""))
                    repassword.setError("أدخل تاكيد المرور");

                else if (!repassword.getText().toString().matches(pass))
                    repassword.setError("كلمتا المرور غير متطابقتين ");
                else {
                    Intent intent = new Intent(Register5Activity.this, Register6Activity.class);
                    intent.putExtra("name", names);
                    intent.putExtra("city", citys);
                    intent.putExtra("mail", mails);
                    intent.putExtra("repass", repassword.getText().toString());
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
