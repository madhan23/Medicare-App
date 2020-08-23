package com.virtusa.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.virtusa.Connection;
import com.virtusa.models.DrugList;
import com.virtusa.recycle.R;

import java.util.ArrayList;
import java.util.List;

public class DrugDetailsLayout extends AppCompatActivity  {
    DrugDetailsAdapter adapter;
    ArrayAdapter listAdapter;
    List<DrugList> druglist=null;
    double total=0;
    TextView totalAmt,drugcontentHeader;
    ListView  notAvailList;
List<String> drugsNotAvail=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details_layout);
        totalAmt=findViewById(R.id.total);
        drugcontentHeader=findViewById(R.id.drugnot_availheader);
	if(Connection.isConnected(DrugDetailsLayout.this)) {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            druglist = (List<DrugList>) bundle.getSerializable("PillsInformation");
            System.out.println(druglist.size());
        }

        for(DrugList drug:druglist){
            if(drug.getIsAvailable().equals("N")){
                drugcontentHeader.setVisibility(View.VISIBLE);
                drugsNotAvail.add(drug.getDrugName());
            }else

                total+=drug.getDrugPriceEach();

        }
        totalAmt.setText(String.valueOf(Math.floor(total)));
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.drugnames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DrugDetailsAdapter(this,druglist);
        recyclerView.setAdapter(adapter);
        //set up for ListView
         notAvailList = (ListView) findViewById(R.id.notavaildrugs);
        listAdapter = new ArrayAdapter<String>(this, R.layout.notavaildrugs, R.id.NotAvaildrugName,drugsNotAvail);
        notAvailList.setAdapter(listAdapter);
	}else
	  Connection.errorMessage(DrugDetailsLayout.this);

    }
    public void onBack(View view) {
        onBackPressed();
    }
    public  void homeLayout(View v){
        if(Connection.isConnected(DrugDetailsLayout.this)) {
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        }else
            Connection.errorMessage(DrugDetailsLayout.this);
    }

}
