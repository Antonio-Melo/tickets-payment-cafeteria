package com.antoniomelo.cafeteria;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersAdaptor extends RecyclerView.Adapter<OrdersAdaptor.ViewHolder> {

    private ArrayList<ListOrder> orders;
    private Context context;

    public OrdersAdaptor(ArrayList<ListOrder> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_orders, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ListOrder order = orders.get(i);
        Integer number = i + 1;

        viewHolder.orderNumber.setText("Order #" + number + "");
        viewHolder.username.setText("Client: " + order.getUsername());
        viewHolder.order.setText("Order: " + order.getOrder());
        viewHolder.deleteButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("SIZE BEFORE DELETE: " + orders.size());
                System.out.println("I: " + i);
                ListOrder order = orders.get(i);
                orders.remove(order);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, orders.size());
                System.out.println("SIZE AFTER DELETE: " + orders.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView orderNumber;
        public TextView username;
        public TextView order;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNumber = (TextView) itemView.findViewById(R.id.orderNumber);
            username = (TextView) itemView.findViewById(R.id.username);
            order = (TextView) itemView.findViewById(R.id.order);
            deleteButton = itemView.findViewById(R.id.deleteOrderButton);
        }
    }
}
