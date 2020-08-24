package com.example.babyapp2;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyapp2.data.DatabaseHandler;
import com.example.babyapp2.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG="MainActivity";
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem,itemQuentity,itemColor,itemSize;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        //Todo: Check whether the items are saved..

        List<Item> items=databaseHandler.getAllItem();
        for(Item item: items){
            Log.d(LOG_TAG,"onCreate " + item.getItemName() + " " + item.getDateItemAdded()+" "+ item.getItemQuentity());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                createPopUpDialogue();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void saveAllItem(View view) {
        //Todo: Save each baby item to DB
        Item item=new Item();
        String newItem = babyItem.getText().toString().trim();
        String newColor = itemColor.getText().toString().trim();
        int quantity = Integer.parseInt(itemQuentity.getText().toString().trim());
        int size = Integer.parseInt(itemSize.getText().toString().trim());

        item.setItemName(newItem);
        item.setItemColor(newColor);
        item.setItemQuentity(quantity);
        item.setItemSize(size);

        databaseHandler.addItem(item);
        Snackbar.make(view, "All Items are Saved",Snackbar.LENGTH_SHORT)
                .show();
        // Move to the next screen..... Detail screen.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    private void createPopUpDialogue() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null); // Just get  a popup from here.
        babyItem=view.findViewById(R.id.baby_item);
        itemQuentity=view.findViewById(R.id.item_quentity);
        itemColor=view.findViewById(R.id.item_color);
        itemSize=view.findViewById(R.id.item_size);

        saveButton=view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!babyItem.getText().toString().isEmpty()
                    &&  !itemQuentity.getText().toString().isEmpty()
                    &&  !itemColor.getText().toString().isEmpty()
                    &&  !itemSize.getText().toString().isEmpty()
                ) {
                    saveAllItem(v);
                } else{
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        builder.setView(view);
        dialog=builder.create();  // creating our dialogue object
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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