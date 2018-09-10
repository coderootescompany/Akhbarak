package com.osamaomar.akhbarak;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.osamaomar.akhbarak.Helper.BroadcastHelper;
import com.osamaomar.akhbarak.Helper.LocationAddressHelper;
import com.osamaomar.akhbarak.Helper.ScrollMapFragment;

public class Map_Select extends android.support.v4.app.Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener{
    private static View view;
    FragmentManager fragmentManager;
    private Typeface flat;
    static boolean active = false;

    TextView select,addresss;

    FragmentManager theFragmentManager = null;
    GoogleMap m_map;
    ImageView mMarker;
    boolean mapReady = false;
    String address;
    Double longitude;
    Double latitude;
    TextView AddressLocation;
    protected Location location;
    private GoogleApiClient mGoogleApiClient;
    private double currentLatitude;
    private double currentLongitude;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient client;
    private LatLng curretLatLng;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private LocationRequest mLocationRequest;
    private Location myLocation;
    ImageView myLocationbtn;
    ScrollView scrolling;

    String send_lat;
    String send_lng;
    private String key;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }

        try {
        view = inflater.inflate(R.layout.activity_map_select, container, false);
        }
        catch (InflateException e) {
            Log.d("gg",e.getMessage());
            /* map is already there, just return view as it is */
        }

        select = view.findViewById(R.id.select);
        addresss = view.findViewById(R.id.address);

        //flat = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JF-Flat-Regular.ttf");
        //select.setTypeface(flat);
        //addresss.setTypeface(flat);
        //if (getArguments().getString("click") != null) {
        //key = getArguments().getString("click");
        //}

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("place_details", Context.MODE_PRIVATE).edit();
                editor.putString("start_lat", send_lat);
                editor.putString("start_lng", send_lng);
                editor.putString("start_address", address);
                editor.apply();
                Intent in = new Intent("place_details");
                BroadcastHelper.sendInform(getActivity(),"place_details",in);
                getActivity().finish();

            }
        });


        theFragmentManager = getChildFragmentManager();
        mMarker =  view.findViewById(R.id.mMarker);
        myLocationbtn =  view.findViewById(R.id.mylocation);
        myLocationbtn.setOnClickListener(this);

        if (servicesOK()) {
            buildGoogleApi();
            mGoogleApiClient.connect();
        }
//        SupportMapFragment mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.pickMaps);
//        if (mapView != null) {
////            mapView.onCreate(null);
////            mapView.onResume();
//            mapView.getMapAsync(this);
//        }

///////////////////////////////////////////// Map scroll
        scrolling = view.findViewById(R.id.scrolling);

        ((ScrollMapFragment) getChildFragmentManager().findFragmentById(R.id.pickMaps)).getMapAsync(this);
        ((ScrollMapFragment) getChildFragmentManager().findFragmentById(R.id.pickMaps))
                .setListener(new ScrollMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scrolling.requestDisallowInterceptTouchEvent(true);
                    }
                });
/////////////////////////////////////////////// End of Map Scroll


        return view;
    }///// End of Fragment OnCreate





    public void permission() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_permission);
        dialog.show();
        Button cancel_log, open;
        ImageView close_dialog;
        open = (Button) dialog.findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                final Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getActivity().getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                getActivity().startActivity(i);

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

    @Override
    public void onPause() {
        super.onPause();
        active = false;

    }


    ///////////////////////////// start of Map location
    protected synchronized void buildGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, getActivity(), ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "please check you opened GPS", Toast.LENGTH_LONG).show();
        }
        return false;
    }


    private void goToAddress(Double mlatitude, Double mLogitude) {
        mMarker.setVisibility(View.VISIBLE);
        m_map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlatitude, mLogitude), 16.0f));

        m_map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition cameraPosition) {
                latitude = cameraPosition.target.latitude;
                longitude = cameraPosition.target.longitude;
                address = LocationAddressHelper.getLocationAddress(getActivity(), cameraPosition.target.latitude, cameraPosition.target.longitude);

                if (address == null) {
                    //address_edit.setText(getString(R.string.cannot_fetch_address));
                    Toast.makeText(getActivity(), "" + getString(R.string.cannot_fetch_address), Toast.LENGTH_SHORT).show();
                } else {
                    addresss.setText(address);
                    //Toast.makeText(getActivity(), "" + address, Toast.LENGTH_SHORT).show();


                    send_lat = String.valueOf(latitude);
                    send_lng = String.valueOf(longitude);

//                    hide_txt_lat.setText(send_lat);
//                    hide_txt_lng.setText(send_lng);
                    //Toast.makeText(getActivity(), send_lat+"+"+send_lng, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    ////////////////6.0+ permission
    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK, Manifest.permission.MANAGE_DOCUMENTS},
                            8);
                }
            }
        }
    }
    ////////////////end of 6.0+ permission


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //// permission
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //// permission
//
      location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

       mLocationRequest = LocationRequest.create();
      mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(1000);
//        LocationServices
//                .FusedLocationApi
//                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (location != null) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();


            myLocation = location;
            // isLocationEnable = true;
            goToAddress(currentLatitude, currentLongitude);
            curretLatLng = new LatLng(currentLatitude,
                    currentLongitude);
            //Toast.makeText(getActivity(), String.valueOf(curretLatLng), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            myLocation = location;
            curretLatLng = new LatLng(location.getLatitude(), location.getLongitude());
          //  getLocationAndAddress();
            //  Toast.makeText(MapsActivity.this,String.valueOf(curretLatLng),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
/*

    public void getLocationAndAddress() {
        final GPSHelper mGpsHelper = new GPSHelper(getActivity());
        mGpsHelper.setOnLocationChangedListener(new GPSHelper.OnLocationChangedListner() {
            @Override
            public void OnLocationChanged(Location location) {
                //update client location
                mGpsHelper.stopListen();
                if (m_map == null)
                    return;
                ///////listner to change address when change map or (camerA)
                m_map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    public void onCameraChange(CameraPosition cameraPosition) {
                        latitude = cameraPosition.target.latitude;
                        longitude = cameraPosition.target.longitude;
                        address = LocationAddressHelper.getLocationAddress(getActivity(), cameraPosition.target.latitude, cameraPosition.target.longitude);
                        if (address == null) {
                            //AddressLocation.setText(getString(R.string.cannot_fetch_address));
                            Toast.makeText(getActivity(), "" + getString(R.string.cannot_fetch_address), Toast.LENGTH_SHORT).show();
                        } else {
                            addresss.setText(address);
//                            Toast.makeText(getActivity(), "" + address, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mMarker.setVisibility(View.VISIBLE);
                m_map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14.0f));
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                address = LocationAddressHelper.getLocationAddress(getActivity(), location.getLatitude(), location.getLongitude());
                //update address String location
                goToAddress(latitude, longitude);
                if (address == null) {
                    addresss.setText(getString(R.string.cannot_fetch_address));
                    Toast.makeText(getActivity(), "" + getString(R.string.cannot_fetch_address), Toast.LENGTH_SHORT).show();
                } else {
                    addresss.setText(address);
//                    Toast.makeText(getActivity(), "" + address, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mGpsHelper.startGpsListen();


    }
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mylocation:
                if (myLocation != null) {
                    LatLng latLang = new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude());
                    goToAddress(latLang.latitude, latLang.longitude);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LocationManager locationManager;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                mapReady = true;
                this.m_map = googleMap;
                m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                m_map.getUiSettings().setZoomControlsEnabled(true);
                if (currentLatitude != 0d && currentLongitude != 0d) {
                    goToAddress(currentLatitude, currentLongitude);
                }
            } else {
                showAlertDialog();
            }
        }


    }
    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning");
        builder.setMessage("Device GPS is currently OFF. Please Turn ON.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }
}



