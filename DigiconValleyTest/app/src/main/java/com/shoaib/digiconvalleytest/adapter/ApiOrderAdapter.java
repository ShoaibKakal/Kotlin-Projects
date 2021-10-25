package com.shoaib.digiconvalleytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.digiconvalleytest.Model.Item;
import com.shoaib.digiconvalleytest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ApiOrderAdapter extends RecyclerView.Adapter<ApiOrderAdapter.ApiOrderViewHolder> {


    private ArrayList<Item> itemArrayList;
    private Context context;

    public ApiOrderAdapter(ArrayList<Item> itemArrayList, Context context) {
        this.itemArrayList = itemArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ApiOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_api_container, parent, false);
        return new ApiOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApiOrderAdapter.ApiOrderViewHolder holder, int position) {

        Item item = itemArrayList.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ApiOrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSuitName, tvOrderId, tvOrderSuitPrice, tvOrderDeliveryDate, tvOrderStatus;
        public ApiOrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvSuitName = itemView.findViewById(R.id.tv_SuitName);
            tvOrderId = itemView.findViewById(R.id.tv_orderId);
            tvOrderSuitPrice = itemView.findViewById(R.id.tv_suitPrice);
            tvOrderDeliveryDate = itemView.findViewById(R.id.tv_dueDate);
            tvOrderStatus = itemView.findViewById(R.id.tv_orderStatus);
        }
        void bindView(Item item)
        {
            tvSuitName.setText(item.getOrderSuit().get(0).getOrderSuitName());
            tvOrderId.setText(item.getOrderSuit().get(0).getItemNumber());
            tvOrderSuitPrice.setText(item.getOrderSuit().get(0).getOrderSuitPrice());
            tvOrderDeliveryDate.setText("Due on "+ item.getOrderSuit().get(0).getDeliveryDate());
//            tvOrderStatus.setText(item.getOrderSuit().get(0).getOrderSuitStatus());
        }
    }



}
