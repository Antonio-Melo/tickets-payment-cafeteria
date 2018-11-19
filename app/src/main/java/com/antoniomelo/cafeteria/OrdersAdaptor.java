package com.antoniomelo.cafeteria;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersAdaptor extends RecyclerView.Adapter<OrdersAdaptor.ViewHolder> {

    private ArrayList<ListOrder> orders;
    private MainActivity mainActivity;

    public OrdersAdaptor(ArrayList<ListOrder> orders, MainActivity mainActivity) {
        this.orders = orders;
        this.mainActivity = mainActivity;
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

                try {
                    ListOrder order = orders.get(i);
                    JSONObject obj = new JSONObject();
                    obj.put("orderId", order.getId());
                    mainActivity.comunicateOrderDone(obj);
                    orders.remove(order);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, orders.size());
                    Snackbar snackbar = Snackbar.make(mainActivity.findViewById(R.id.main_activity_layout),
                            R.string.order_done, Snackbar.LENGTH_LONG);

                    snackbar.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(JSONArray orders) {
        for(int index=0; index < orders.length(); index++){
            try {
                JSONObject order = orders.getJSONObject(index);
                if (!hasId(order.getString("_id"))) {
                    this.orders.add(new ListOrder(order.getString("_id"), (index + 1), order.getString("username"),
                            order.getString("order")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       /* if (this.orders.size() == 0) {
            mainActivity.findViewById(R.id.no_orders).setVisibility(View.VISIBLE);
        }
*/
        notifyDataSetChanged();
    }

    public boolean hasId(String id) {
        for(ListOrder order: this.orders){
            if (order.getId().equals(id)) return true;
        }
        return false;
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
