package com.osamaomar.akhbarak.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.osamaomar.akhbarak.Adapters.ImagesAdapterForAddImages;
import com.osamaomar.akhbarak.Helper.BroadcastHelper;
import com.osamaomar.akhbarak.R;
import com.osamaomar.akhbarak.model.ItemImage;
import com.osamaomar.akhbarak.model.ItemList;

import java.util.ArrayList;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    // List<MultipartBody.Part> parts = new ArrayList<>();
    ArrayList<ItemImage> Pathitems = new ArrayList<>();
    private List<ItemList> mItemList = new ArrayList<>();
    private GridView gvGallery;
    RecyclerView recyclerViewGallery;
    TextView locationtxt;
    private ImagesAdapterForAddImages galleryAdapter2;
    int PICK_IMAGE_MULTIPLE = 1;
    int REQUEST_TAKE_GALLERY_VIDEO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gvGallery = findViewById(R.id.gv);
        recyclerViewGallery = findViewById(R.id.images_recycler);
        locationtxt= findViewById(R.id.location);
    }


    public void permission() {
        final Dialog dialog = new Dialog(CreatePostActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                i.setData(Uri.parse("package:" + CreatePostActivity.this.getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                CreatePostActivity.this.startActivity(i);
            }
        });

        cancel_log = dialog.findViewById(R.id.cancel_log);
        cancel_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close_dialog = dialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void getImages(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(CreatePostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            permission();
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                mArrayUri.clear();
                // Get the Image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                //  imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){
                    Uri mImageUri=data.getData();
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    // imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    galleryAdapter2 = new ImagesAdapterForAddImages(getApplicationContext(),mArrayUri);
                    // gvGallery.setAdapter(galleryAdapter);
                    recyclerViewGallery.setAdapter(galleryAdapter2);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                    if (mArrayUri.size() > 0) {
                        for (int i = 0; i < mArrayUri.size(); i++) {
                            //  parts.add(prepareFilePart("image" + "[" + i + "]", mArrayUri.get(i)));
                            Log.i("[5]", "[" + i + "]");
                        }
                    }
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            //  imageEncoded  = cursor.getString(columnIndex);
                            //      imagesEncodedList.add(imageEncoded);
                            cursor.close();
                            galleryAdapter2 = new ImagesAdapterForAddImages(getApplicationContext(),mArrayUri);
                            recyclerViewGallery.setAdapter(galleryAdapter2);
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                    if (mArrayUri.size() > 0) {
                        for (int i = 0; i < mArrayUri.size(); i++) {
                            // parts.add(prepareFilePart("image" + "[" + i + "]", mArrayUri.get(i)));
                        }
                    }
                }
            } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO)
            {
                    Uri selectedImageUri = data.getData();
                    // OI FILE Manager
                String  filemanagerstring = selectedImageUri.getPath();
                    // MEDIA GALLERY
                 String   selectedImagePath = getPath(selectedImageUri);
//                    if (selectedImagePath != null) {
//
//                        Intent intent = new Intent(HomeActivity.this,
//                                VideoplayAvtivity.class);
//                        intent.putExtra("path", selectedImagePath);
//                        startActivity(intent);
//                    }

            }

         else {
                Toast.makeText(this, getResources().getString(R.string.notselect),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_LONG)
                    .show();
            Log.v("exc",e.getMessage());

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public void openmap(View view) {
        startActivity(new Intent(CreatePostActivity.this,MapActivity.class));

    }

    public void getVideos(View view) {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }

    Receiver receiver;
    boolean isReciverRegistered = false;

    @Override
    public void onResume() {
        super.onResume();
        if (receiver == null) {
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            registerReceiver(receiver, filter);
            isReciverRegistered = true;
        }
    }

    @Override
    public void onDestroy() {
        if (isReciverRegistered) {
            if (receiver != null)
                unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

   private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                Log.v("receive", methodName);
                switch (methodName) {
                    case "place_details":

                        SharedPreferences search_prefs = CreatePostActivity.this.getSharedPreferences("place_details", Context.MODE_PRIVATE);
                      String  start_lat = search_prefs.getString("start_lat", null);
                      String  start_log = search_prefs.getString("start_lng", null);
                      String  address = search_prefs.getString("start_address", null);
                        locationtxt.setText(address);

                    default:
                        break;
                }
            }
        }
    }

}
