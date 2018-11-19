package com.antoniomelo.cafeteria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String LOCAL_SERVER_IP = "10.227.155.2:3000";
    private static final String PUBLIC_SERVER_IP = "tickets-payment-rest-api.herokuapp.com";
    private static String server_ip = PUBLIC_SERVER_IP;

    private RecyclerView recyclerView;
    private OrdersAdaptor adapter;
    private ArrayList<ListOrder> orders;

    final long timeInterval = 5000;
    Runnable runnable = new Runnable() {

        public void run() {
            while (true) {
                Log.d("CAFETARIA", "boas");
                getOrder();
                try {
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<ListOrder>();

        /* Add elements to list */

       /* for(int i = 0; i < 10; i++)
            orders.add(new ListOrder(i,"António","Drink"));*/

        adapter = new OrdersAdaptor(orders, this);
        recyclerView.setAdapter(adapter);

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void getOrder() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + server_ip + "/orders/";

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("CAFETARIA", response.toString());
                try {
                    adapter.setOrders(response.getJSONArray("orders"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("CAFETARIA", "Getting orders error");
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void comunicateOrderDone(JSONObject order) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + server_ip +"/validation/order";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, order, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("CAFETARIA", "Order done");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("CAFETARIA", "Order error");

            }
        });

        queue.add(jsonObjectRequest);
    }
  }
