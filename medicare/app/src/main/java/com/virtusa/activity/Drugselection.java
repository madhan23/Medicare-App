package com.virtusa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtusa.Config;
import com.virtusa.Connection;
import com.virtusa.models.ShopDetailList;
import com.virtusa.recycle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Drugselection extends AppCompatActivity {
    RequestQueue requestQueue;
    ProgressDialog pd;
    List<String> druglist=new ArrayList<String>();
    List<String>addeddrug=new ArrayList<String>();
    private AutoCompleteTextView drugName;
    ListView simpleList;
    ArrayAdapter<String> adapter,arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugselection);
        pd = new ProgressDialog(Drugselection.this);
        if(Connection.isConnected(Drugselection.this)) {
            pd.setMessage("Loading DrugList...");
            pd.show();
            getDrugList();
            drugName = findViewById(R.id.drug_list);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, druglist);
            drugName.setAdapter(adapter);
            simpleList = (ListView) findViewById(R.id.simpleListView);
            simpleList.setClickable(true);
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.selecteddrugs, R.id.textView_selected, addeddrug);
        }else{
            Toast.makeText(this, "Type valid Drug Name", Toast.LENGTH_SHORT).show();
        }

    }
    public  void addDrugs(View v){
        if (Connection.isConnected(Drugselection.this)) {
            if(drugName.getText().toString().trim().length()!=0 && druglist.contains(drugName.getText().toString().trim())){
                if(addeddrug!=null){
                    if(addeddrug.contains(drugName.getText().toString().trim())){
                        drugName.setText("");

                    }else
                        addeddrug.add(drugName.getText().toString().trim());
                        drugName.setText("");
                }else {
                    addeddrug.add(drugName.getText().toString().trim());
                    drugName.setText("");
                }
                simpleList.setAdapter(arrayAdapter);
            }else{
                Toast.makeText(this, "Please choose Valid drug", Toast.LENGTH_SHORT).show();
                drugName.setText("");
            }

            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    String selectedDrug = (String) parent.getItemAtPosition(position);
                    addeddrug.remove(selectedDrug);
                    simpleList.setAdapter(arrayAdapter);


                }
            });
        }else
            Connection.errorMessage(Drugselection.this);

    }
    public  void fetchPharamacydetails(View v){
        if(Connection.isConnected(Drugselection.this)){
            if(addeddrug.size()!=0) {
                getPharamacyList();
            }
            else
                Toast.makeText(this, "Please select atleast one drug", Toast.LENGTH_SHORT).show();
        }else
            Connection.errorMessage(Drugselection.this);
    }


    private  void getDrugList() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.DrugListUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i(" DrugList ",response.toString());
                    JSONArray jsonarray=jsonObject.getJSONArray("druglist");
                    if(jsonarray!=null) {
                        for(int i=0;i<jsonarray.length();druglist.add(jsonarray.get(i++).toString()));

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               Log.e(" DrugList Error",error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }
    public  void getPharamacyList(){
        pd.setMessage("Loading...");
        pd.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            JSONArray list = new JSONArray(addeddrug);
            String area=getIntent().getStringExtra("place").trim();
            jsonBody.put("area",area);
            jsonBody.put("drugName",list);
            final String mRequestBody = jsonBody.toString();
            Log.i("JSON Request",mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.pharamacyUrl , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<ShopDetailList> shoplist;
                    pd.dismiss();
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        Gson gson = new Gson();
                        if(jsonarray!=null)
                        {   // converting json response to pojo class
                            TypeToken<List<ShopDetailList>> token = new TypeToken<List<ShopDetailList>>(){};
                            shoplist = gson.fromJson(response, token.getType());

                            if(Connection.isConnected(Drugselection.this)){
                                Intent i=new Intent(Drugselection.this, RecycleViewLayout.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("shopdetails", shoplist);
                                i.putExtras(bundle);
                                startActivity(i);
                            }else
                                Connection.errorMessage(Drugselection.this);
                        }



                    } catch (JSONException e) {
                        Toast.makeText(Drugselection.this, "Invalid DrugList", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Log.e("LOG_VOLLEY Post Request", error.toString());
                    Toast.makeText(Drugselection.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
