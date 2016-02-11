package com.example.sravan.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Home_Screen extends AppCompatActivity {

    Button back;
    Button get;
    EditText input;
    Home_Screen obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        obj=this;
        back=(Button) findViewById(R.id.logOut);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goBack(v);
            }
        });
        get=(Button) findViewById(R.id.convert);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDetails(v);
            }
        });
        input=(EditText)findViewById(R.id.convertText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void goBack(View view)
    {
        Intent intent=new Intent(this,Log_in.class);
        startActivity(intent);
    }

    public void getDetails(View v)
    {
        OkHttpClient client = new OkHttpClient();
        try {

            String getIRL="https://restcountries.eu/rest/v1/alpha/"+input.getText();

             Request request = new Request.Builder()
                    .url(getIRL)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String firstResponse = response.body().string();
                    final TextView sample1, sample2, sample3,sample4;
                    final String convertedText1, convertedText2, convertedText3,convertedText4, status;

                    try {
                        jsonResult = new JSONObject(firstResponse);

                        if (jsonResult.length()<100)
                        {
                            new AlertDialog.Builder(obj).setTitle("OOPS..!!").setMessage("No Country with that").setNeutralButton("Close", null).show();
                        }
                        else {
                            convertedText1 = jsonResult.getString("name");
                            sample1 = (TextView) findViewById(R.id.country);

                            convertedText2 = jsonResult.getString("capital");
                            sample2 = (TextView) findViewById(R.id.capital);

                            convertedText3 = jsonResult.getString("region");
                            sample3 = (TextView) findViewById(R.id.region);

                            convertedText4 = jsonResult.getString("population");
                            sample4 = (TextView) findViewById(R.id.pop);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sample1.setText("Country: " + convertedText1);
                                    sample2.setText("Capital: " + convertedText2);
                                    sample3.setText("Region: " + convertedText3);
                                    sample4.setText("Population: " + convertedText4);
                                }
                            });
                        }

                    } catch (JSONException ex) {

                        ex.printStackTrace();
                        new AlertDialog.Builder(obj).setTitle("OOPS..!!").setMessage("No Country with that").setNeutralButton("Close", null).show();
                    }

                }
            });
        }  catch (Exception ex) {
           Log.d("Exception",ex.getMessage());
            }

    }

}
