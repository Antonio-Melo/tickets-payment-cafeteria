package com.antoniomelo.cafeteria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<ListOrder> orders;
    public Boolean validadeVouchersStatus = false;
    public Boolean createTransactionStatus = false;

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

        //System.out.println(validateVouchers());
        System.out.println(createTransaction());
    }

    private Boolean validateVouchers(){
        try {
            String bodyString = "{\"uuid\": \"3309fb76-107f-4f7d-aeaa-afc3cbaaf8df\", \"vouchers\": [\"fcac5224-4890-4e14-98a1-024df8ff88e7\", \"3e257e02-790b-4fe0-8fbe-1f02f2ee7e95\"]}";
            StringEntity body = new StringEntity(bodyString);
            body.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(null,"http://10.0.2.2:3000/validation/vouchers", body, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("STATUS CODE: " +statusCode);
                    validadeVouchersStatus = true;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("STATUS CODE: " +statusCode);
                    System.out.println(error);
                    validadeVouchersStatus = false;
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            validadeVouchersStatus = false;
            return validadeVouchersStatus;
        }
        return validadeVouchersStatus;
    }

    private Boolean createTransaction() {
        try {
            String bodyString = "{\n" +
                    "  \"uuid\": \"3309fb76-107f-4f7d-aeaa-afc3cbaaf8df\",\n" +
                    "  \"order\": {\n" +
                    "    \"coffee\": \"2\",\n" +
                    "    \"drink\": \"3\",\n" +
                    "    \"popcorn\": \"1\",\n" +
                    "    \"sandwich\": \"1\"\n" +
                    "  },\n" +
                    "  \"vouchers\": [\"fcac5224-4890-4e14-98a1-024df8ff88e7\", \"3e257e02-790b-4fe0-8fbe-1f02f2ee7e95\"]\n" +
                    "}";

            StringEntity body = new StringEntity(bodyString);
            body.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(null,"http://10.0.2.2:3000/users/order", body, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("STATUS CODE: " +statusCode);
                    createTransactionStatus = true;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("STATUS CODE: " +statusCode);
                    System.out.println(error);
                    createTransactionStatus = false;
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            validadeVouchersStatus = false;
            return createTransactionStatus;
        }
        return createTransactionStatus;
    }
}
