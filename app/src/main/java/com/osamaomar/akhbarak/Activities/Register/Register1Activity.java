package com.osamaomar.akhbarak.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.osamaomar.akhbarak.Activities.LoginActivity;
import com.osamaomar.akhbarak.R;

public class Register1Activity extends AppCompatActivity {



    EditText  nametxt;
    TextView nextttx,gotologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        nametxt=findViewById(R.id.name);
        nextttx=findViewById(R.id.next);
        gotologin=findViewById(R.id.gotologin);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(Register1Activity.this, LoginActivity.class));
            }
        });

        nextttx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nametxt.getText().equals(" "))
                    nametxt.setError("ادخل الاسم");
                else
                {
                    Intent intent=new Intent(Register1Activity.this,Register2Activity.class);
                    intent.putExtra("name",nametxt.getText().toString());
                    startActivity(intent);
                }
            }
        });


    }
}
