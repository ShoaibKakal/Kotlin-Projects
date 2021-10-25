package com.shoaib.digiconvalleytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.digiconvalleytest.Model.Order;
import com.shoaib.digiconvalleytest.R;
import com.shoaib.digiconvalleytest.listeners.OrderListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.R;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrderViewHolder> {


    private ArrayList<Order> orders;
    private OrderListener orderListener;
    public OrdersListAdapter(ArrayList<Order> orders, OrderListener orderListener) {
        this.orders = orders;
        this.orderListener = orderListener;
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.shoaib.digiconvalleytest.R.layout.item_orders_container, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersListAdapter.OrderViewHolder holder, int position) {

        Order order = orders.get(position);
        holder.bindItem(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name, price;
        public OrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(com.shoaib.digiconvalleytest.R.id.image_item);
            name = itemView.findViewById(com.shoaib.digiconvalleytest.R.id.tv_name);
            price = itemView.findViewById(com.shoaib.digiconvalleytest.R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderListener.onOrderItemClicked(orders.get(getAdapterPosition()));
                }
            });
        }

        public void bindItem(Order order){
            imageView.setImageResource(order.getImageId());
            name.setText(order.getName());
            price.setText("Rs. "+ order.getPrice()+ "( "+ order.getItems() + " Items ) ");
        }
    }
}
