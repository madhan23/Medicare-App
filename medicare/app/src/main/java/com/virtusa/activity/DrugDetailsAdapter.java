package com.virtusa.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virtusa.models.DrugList;
import com.virtusa.recycle.R;

import java.util.List;

public class DrugDetailsAdapter extends RecyclerView.Adapter<DrugDetailsAdapter.ViewHolder>  {
    private List<DrugList> mData;
    private LayoutInflater mInflater;

    DrugDetailsAdapter(Context context, List<DrugList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.drugdetailsrow, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //DrugList druglist= (DrugList) mData.get(position).getDrugList();
        if(mData.get(position).getIsAvailable().equals("Y")) {
            holder.drugName.setText(mData.get(position).getDrugName());
            holder.drugPrice.setText(String.valueOf(mData.get(position).getDrugPriceEach()));
        }else{
            holder.drugName.setText("");
            holder.drugPrice.setText("");
        }


    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

// stores and recycles views as they are scrolled off screen
class ViewHolder extends RecyclerView.ViewHolder {
    TextView drugName, drugPrice;

    ViewHolder(View itemView) {
        super(itemView);
        drugName = itemView.findViewById(R.id.drugNameList);
        drugPrice = itemView.findViewById(R.id.drugPriceDetail);
    }

}
}
