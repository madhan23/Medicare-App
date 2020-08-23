package com.virtusa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.virtusa.Connection;
import com.virtusa.models.DrugList;
import com.virtusa.models.ShopDetailList;
import com.virtusa.recycle.R;
import java.util.Collections;
import java.util.List;

public class RecycleViewLayout extends AppCompatActivity implements PharamacyiewAdapter.ItemClickListener {

    PharamacyiewAdapter adapter;
    Button priceInfo,distanceInfo;
    List<ShopDetailList> shopListdetails=null;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_layout);
        if(Connection.isConnected(RecycleViewLayout.this))
        {
            distanceInfo = findViewById(R.id.distance);
            priceInfo = findViewById(R.id.price);
            priceInfo.setBackgroundColor(Color.parseColor("#39aed1")); // From android.graphics.Color
            priceInfo.setTextColor(Color.parseColor("#ffffff"));
            SharedPreferences values=getSharedPreferences("LocationInformations",0);

            Bundle bundle = this.getIntent().getExtras();
            if (bundle != null) {
                shopListdetails= (List<ShopDetailList>) bundle.getSerializable("shopdetails");
            }
// calculating distance from source to destination place
            for(ShopDetailList shoplist:shopListdetails) {
                double lat1,lat2,lon1,lon2,total=0;
                lat1 = Math.toRadians(Double.valueOf(values.getString("Latitude","0")));
                lon1 = Math.toRadians(Double.valueOf(values.getString("Longitude","0")));
                lat2 = Math.toRadians(Double.valueOf(shoplist.getLatitude()));
                lon2 = Math.toRadians(Double.valueOf(shoplist.getLongtitude()));

                double earthRadius = 6371.01; //Kilometers
                String distance=String.valueOf( earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2)));
                int dindex=distance.indexOf(".");
                // set distance value
                shoplist.setKm(Double.parseDouble(distance.substring(0,dindex+3)));
                //adding total cost
                for(DrugList dlist:shoplist.getDrugList()){
                    if(dlist.getIsAvailable().equals("Y"))
                        total+=dlist.getDrugPriceEach();
                }
                shoplist.setTotalAmount(Math.floor(total));
            }
// sorting list based on price wise..
            Collections.sort(shopListdetails,(ShopDetailList o1, ShopDetailList  o2)->Double.compare(o1.getTotalAmount(),o2.getTotalAmount()));

            // set up the RecyclerView
            recyclerView = findViewById(R.id.shopdetails);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PharamacyiewAdapter(this, shopListdetails);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

        }else
            Connection.errorMessage(RecycleViewLayout.this);


    }

    @Override
    public void onItemClick(View view, int position) {
        if(Connection.isConnected(RecycleViewLayout.this)){
            Intent i=new Intent(this,ShopInfoActivity.class);
            ShopDetailList shopInfomartion=adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ShopInfomartion", shopInfomartion);
            i.putExtras(bundle);
            startActivity(i);
        }else
            Connection.errorMessage(RecycleViewLayout.this);

    }

    public void PriceInformation(View v){
        distanceInfo.setBackgroundColor(0);
        distanceInfo.setTextColor(Color.parseColor("#39aed1"));
        priceInfo.setBackgroundColor(Color.parseColor("#39aed1")); // From android.graphics.Color
        priceInfo.setTextColor(Color.parseColor("#ffffff"));
        //  sort a list based on price wise
        Collections.sort(shopListdetails,(ShopDetailList o1, ShopDetailList  o2)->Double.compare(o1.getTotalAmount(),o2.getTotalAmount()));
        recyclerView.setAdapter(adapter);
    }
    public void DistanceInformation(View v){
       distanceInfo.setBackgroundColor(Color.parseColor("#39aed1")); // From android.graphics.Color
      distanceInfo.setTextColor(Color.parseColor("#ffffff"));
        priceInfo.setBackgroundColor(0);
        priceInfo.setTextColor(Color.parseColor("#39aed1"));
        //  sort a list  based  on distance wise
        Collections.sort(shopListdetails,(ShopDetailList o1, ShopDetailList  o2)->Double.compare(o1.getKm(),o2.getKm()));
        recyclerView.setAdapter(adapter);
    }
public  void navigate(View v){
        if(Connection.isConnected(RecycleViewLayout.this)) {
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        }else
            Connection.errorMessage(RecycleViewLayout.this);
}

}