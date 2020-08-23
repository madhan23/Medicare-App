package com.virtusa.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.virtusa.models.ShopDetailList;
import com.virtusa.recycle.R;
import java.util.List;

public class PharamacyiewAdapter extends RecyclerView.Adapter<PharamacyiewAdapter.ViewHolder> {
    private List<ShopDetailList> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    PharamacyiewAdapter(Context context, List<ShopDetailList> shopData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = shopData;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //DrugList druglist= (DrugList) mData.get(position).getDrugList();
        holder.shopName.setText("Shop:"+mData.get(position).getPharmacyName());
        holder.price.setText("Price:"+String.valueOf(mData.get(position).getTotalAmount()+" Rs"));
        holder.distance.setText("Distance :"+String.valueOf(mData.get(position).getKm()+" KM"));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shopName,price,distance;
        ViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopname);
            price = itemView.findViewById(R.id.distance);
            distance = itemView.findViewById(R.id.price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
   ShopDetailList getItem(int id) {
 return mData.get(id);
 }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
