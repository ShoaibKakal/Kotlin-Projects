package com.shoaib.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<ItemData> itemDataArrayList;
    private Context context;


    public OrderAdapter(ArrayList<ItemData> itemDataArrayList, Context context) {
        this.itemDataArrayList = itemDataArrayList;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull OrderAdapter.OrderViewHolder holder, int position) {

        ItemData itemData = itemDataArrayList.get(position);
        holder.bindData(itemData);
    }

    @Override
    public int getItemCount() {
        return itemDataArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderNo, orderDesc, orderDate;
        public OrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            orderNo = itemView.findViewById(R.id.tv_orderNo);
            orderDesc = itemView.findViewById(R.id.tv_orderDesc);
            orderDate = itemView.findViewById(R.id.tv_orderDate);
        }

        void bindData(ItemData itemData)
        {
            orderNo.setText(itemData.getOrderSuit().get(0).getCustomerName());
            orderDesc.setText(itemData.getOrderDesc());
            orderDate.setText(itemData.getOrderDate());
        }
    }
}
