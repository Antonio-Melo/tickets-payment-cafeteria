package com.antoniomelo.cafeteria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<ListOrder> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<ListOrder>();

        /* Add elements to list */

        for(int i = 0; i < 10; i++)
            orders.add(new ListOrder(i,"António","Drink"));

        adapter = new OrdersAdaptor(orders, this);
        recyclerView.setAdapter(adapter);
    }
}
