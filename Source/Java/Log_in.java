package com.example.sravan.testapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;
import org.w3c.dom.Text;

public class Log_in extends AppCompatActivity {

  public Button next;
   public EditText usName,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

    next=(Button) findViewById(R.id.login);

        usName= (EditText) findViewById(R.id.userName);
        pwd=(EditText) findViewById(R.id.password);

        next.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v)
        {
            validate(next);
        }
        });


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


    public  void validate(View v)

    {
        String s1,s2;
    s1=usName.getText().toString();
        s2=pwd.getText().toString();
        Log.d("Username",s1);
        Log.d("PWD",s2);


        if((s1.length()>0 && s2.length()>0))
        {
            if (s1.equals("Admin") && s2.equals("Admin"))
            {
                Log.d("Message","Success");
                Intent intent = new Intent(this, Home_Screen.class);
                startActivity(intent);
            }
            else
            {
                Log.d("Remove","Password mismatch");
            }
        }
        else {
            new AlertDialog.Builder(this).setTitle("OOPS..!!").setMessage("Please try again").setNeutralButton("Close", null).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
