package com.osamaomar.akhbarak.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.osamaomar.akhbarak.Activities.Register.Register1Activity;
import com.osamaomar.akhbarak.R;

public class LoginActivity extends AppCompatActivity {

    TextView gotoregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoregister = findViewById(R.id.gotoregister);
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(LoginActivity.this, Register1Activity.class));
            }
        });
    }
}
