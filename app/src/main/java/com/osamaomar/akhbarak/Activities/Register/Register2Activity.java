package com.osamaomar.akhbarak.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.osamaomar.akhbarak.R;

public class Register2Activity extends AppCompatActivity {

    EditText city;
    TextView name,next,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        city = findViewById(R.id.city);
        name = findViewById(R.id.name);
        next =findViewById(R.id.next);
        back =findViewById(R.id.back);
        final String names=getIntent().getStringExtra("name");
        name.setText(names);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (city.getText().toString().matches(""))
                    city.setError("ادخل المدينة");
                else {
                    Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                    intent.putExtra("name", names);
                    intent.putExtra("city", city.getText().toString());
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
