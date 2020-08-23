package com.virtusa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.virtusa.Connection;
import com.virtusa.models.DrugList;
import com.virtusa.models.ShopDetailList;
import com.virtusa.recycle.R;
import java.io.Serializable;
import java.util.List;

public class ShopInfoActivity extends AppCompatActivity {
    TextView addressInfo,mobileNo,timings,shopName,webinfo;
    Button drugdetails;
    ShopDetailList shopinfo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        shopName = findViewById(R.id.shop);
        addressInfo = findViewById(R.id.address);
        mobileNo = findViewById(R.id.phoneno);
        timings = findViewById(R.id.time);
        webinfo = findViewById(R.id.website);
        drugdetails = findViewById(R.id.druginfo);
        callPermission();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            shopinfo = (ShopDetailList) bundle.getSerializable("ShopInfomartion");

        }
        //  passing values into different activity 0 for private mode
        SharedPreferences values = getSharedPreferences("LocationInformations", 0);
        shopName.setText(shopinfo.getPharmacyName());
        addressInfo.setText(shopinfo.getAddressLine());
        timings.setText(shopinfo.getOpenTime() + "-" + shopinfo.getClosedTime());
        mobileNo.setText(shopinfo.getPhone());
        webinfo.setText(shopinfo.getWebSite());
    }
    public void showCustomDialog(View v) {
        if(Connection.isConnected(ShopInfoActivity.this)){
            Intent i=new Intent(this,DrugDetailsLayout.class);
            Bundle bundle = new Bundle();
            List<DrugList> druglist=shopinfo.getDrugList();
            bundle.putSerializable("PillsInformation", (Serializable) druglist);
            i.putExtras(bundle);
            startActivity(i);
        }else
            Connection.errorMessage(ShopInfoActivity.this);
    }
    public  void navigation (View view){
        if(Connection.isConnected(ShopInfoActivity.this)){
            Intent i=new Intent(this,MapActivity.class);
            i.putExtra("shop_Latitude",Double.valueOf(shopinfo.getLatitude()));
            i.putExtra("shop_Longitude",Double.valueOf(shopinfo.getLongtitude()));
            i.putExtra("shop_name",shopinfo.getPharmacyName());
            startActivity(i);
        }
        else
            Connection.errorMessage(ShopInfoActivity.this);
}

public  void makeCall(View v){
    if (ContextCompat.checkSelfPermission(ShopInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "please allow permission for make a call", Toast.LENGTH_SHORT).show();
    }
    else
    {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+mobileNo.getText().toString()));//change the number
        startActivity(callIntent);
    }

}
    public  void homeNavigate(View v){
        if(Connection.isConnected(ShopInfoActivity.this)) {
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        }else
            Connection.errorMessage(ShopInfoActivity.this);
    }
    private void callPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    public  void openBrowser(View v){

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+shopinfo.getWebSite())));

    }
}
