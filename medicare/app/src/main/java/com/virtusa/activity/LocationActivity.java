package com.virtusa.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.virtusa.Config;
import com.virtusa.Connection;
import com.virtusa.recycle.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private Geocoder geocoder;
    private FusedLocationProviderClient client;
    private List<Address> addressList;
    RequestQueue requestQueue;
    ProgressDialog pd;
    private List<String> arealist;
    private TextView currentlocation;
    String place="";
    Button submit;
    AutoCompleteTextView areaName;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // checking internet connection......
        if(Connection.isConnected(LocationActivity.this))  {
            getAreaList();
            pd= new ProgressDialog(LocationActivity.this);
            pd.setMessage("Loading AreaList...");
            pd.show();
            submit=findViewById(R.id.currentloc_btn);
            currentlocation=findViewById(R.id.loc_area);
            // sharing content one activity any other activity
            sharedpreferences = getApplicationContext().getSharedPreferences("LocationInformations", Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            arealist=new ArrayList<>();
            areaName = findViewById(R.id.area_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arealist);
            areaName.setAdapter(adapter);
            intent = new Intent(this, Drugselection.class);
            areaName.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    String areaDetails=areaName.getText().toString();
                    if(areaDetails.length()==0)
                        submit.setEnabled(true);
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    currentlocation.setText("");
                    submit.setEnabled(false);

                }
            });
            // while page loads itself get location permisssion......
            requestPermission();
            client = LocationServices.getFusedLocationProviderClient(this);


        }else{
            Connection.errorMessage(LocationActivity.this);

        }



    }

public void locationDirection( String area)
{
        try {
            Geocoder gc = new Geocoder(this);
            List<Address> addrs=gc.getFromLocationName(area, 1); // get the found Address Objects
        Address address=addrs.get(0);
            LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
            Log.e("Area for lat/lng",latlng.latitude+""+latlng.longitude);
            editor.putString("Latitude",String.valueOf(latlng.latitude));
            editor.putString("Longitude",String.valueOf(latlng.longitude));
            editor.apply();
        } catch (IOException e) {
            Log.e("Area Geocode",e.toString());
        }

}
    public void currentLocationTracker(View v) {
        if(Connection.isConnected(LocationActivity.this)) {
            geocoder = new Geocoder(this, Locale.getDefault());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Allow Location Permission to access", Toast.LENGTH_SHORT).show();
                return;
            }
            client.getLastLocation().addOnSuccessListener(LocationActivity.this, new OnSuccessListener<Location>() {
                double longitude = 0, latitude = 0;

                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        try {
                            addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String fullAddressDetails = addressList.get(0).getAddressLine(0);
                            String address[] = fullAddressDetails.split(",");
                            place = address[address.length - 4];
                            currentlocation.setText(fullAddressDetails);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        editor.putString("Latitude", String.valueOf(latitude));
                        editor.putString("Longitude", String.valueOf(longitude));
                        editor.apply();
                    }
                    Log.i("Latitude", longitude + "Longitude" + latitude);
                }

            });
        }else
            Connection.errorMessage(LocationActivity.this);

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }


public void onSubmit(View v) {
// checking internet connection......
    if(Connection.isConnected(LocationActivity.this)) {
        if (TextUtils.isEmpty(currentlocation.getText().toString()) && TextUtils.isEmpty(areaName.getText().toString()))
            Toast.makeText(this, "Please choose your current Location..", Toast.LENGTH_SHORT).show();
        else {
            if (areaName.getText().toString().trim().length() != 0) {
                if(arealist.contains(areaName.getText().toString().trim()))
                {
                    locationDirection(areaName.getText().toString().trim());
                    place = areaName.getText().toString().trim();
                    intent.putExtra("place", place);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "please choose a correct Area", Toast.LENGTH_SHORT).show();
                    areaName.setText("");
                }
            }
            if(currentlocation.getText().toString().trim().length()!=0 && areaName.getText().toString().trim().length()==0){
                intent.putExtra("place", place);
                startActivity(intent);
            }
        }

    }else{
           Connection.errorMessage(LocationActivity.this);
    }
}



    private void getAreaList() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.areaListUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.i("AreaList", response);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray jsonarray =object.getJSONArray("area");
           if(jsonarray.length()!=0){
               for(int i=0;i<jsonarray.length();arealist.add(jsonarray.get(i++).toString()));
           }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    Log.e("AreaList", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AreaList", error.toString());
            }
        });
        requestQueue.add(stringRequest);
        }

    // making a close app dialog box......
    @Override
    public void onBackPressed() {
        // create a Alertdialog box
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Confirm Exit...!");
        alertdialog.setMessage("Are you sure you want to exit app");
        alertdialog.setCancelable(false);
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LocationActivity.this, "You Clicked Cancel Button", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertdialog.create();
        alert.show();
    }


}