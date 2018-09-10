package com.osamaomar.akhbarak.Activities.Register;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.osamaomar.akhbarak.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Register6Activity extends AppCompatActivity {

    TextView next,back,name,city,mail,password,repassword;
    ImageView upload;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register6);

        final String names=getIntent().getStringExtra("name");
        final String citys=getIntent().getStringExtra("city");
        final String mails=getIntent().getStringExtra("mail");
        final String pass=getIntent().getStringExtra("repass");

        mail =findViewById(R.id.mail);
        next =findViewById(R.id.next);
        back =findViewById(R.id.back);
        name =findViewById(R.id.name);
        city =findViewById(R.id.city);
        password =findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        upload=findViewById(R.id.imgupload);

        name.setText(names);
        city.setText(citys);
        mail.setText(mails);

        StringBuilder repass2= new StringBuilder();
        for (int i=0;i<pass.length();i++)
            repass2.append("*");

        repassword.setText(repass2.toString());
        password.setText(repass2.toString());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getProfileImage(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(Register6Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            permission();
        } else {

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(Register6Activity.this);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                upload.setImageURI(uri);////set the image after taking it
                //part=prepareFilePart("photo",uri);

                //  Log.d("uri",UploadHelper.getFileDataFromDrawable(ProfileActivity.this, uri)) ;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void permission() {
        final Dialog dialog = new Dialog(Register6Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_permission_cam);
        dialog.show();
        Button cancel_log, open;
        ImageView close_dialog;
        open =  dialog.findViewById(R.id.open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                final Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + Register6Activity.this.getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                Register6Activity.this.startActivity(i);
            }
        });

        cancel_log = (Button) dialog.findViewById(R.id.cancel_log);
        cancel_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close_dialog = (ImageView) dialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}
