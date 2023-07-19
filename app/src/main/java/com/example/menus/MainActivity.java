package com.example.menus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menus.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemList;
    private String Name="",ph="";
    private int selectedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemIndex = position;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_call) {
            callnum();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addit) {
            showAddItemDialog();
            return true;
        } else if (item.getItemId() == R.id.delit) {
            delete();
            return true;}
        else if (item.getItemId() == R.id.pop) {
            Intent u=new Intent(MainActivity.this,ConversionActivity.class);
            startActivity(u);

            return true;}
        return super.onOptionsItemSelected(item);
    }
    private void callnum() {
        String selectedItem = itemList.get(selectedItemIndex);
        String[] parts = selectedItem.split(" - ");
        String phoneNumber = parts[1].trim();

        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
        startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app found to handle the call.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item");

        View view = getLayoutInflater().inflate(R.layout.add, null);
        final EditText nameEditText = view.findViewById(R.id.name);
        final EditText numberEditText = view.findViewById(R.id.num);

        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString().trim();
                String number = numberEditText.getText().toString().trim();

                if (!name.isEmpty() && !number.isEmpty()) {
                    String item = name + " - " + number;
                    itemList.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
        });

            // Create the negative button and its listener.
            builder.setNegativeButton("Cancel", null);

            // Create and show the popup dialog.
            AlertDialog dialog = builder.create();
            dialog.show();


        }
    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item");
        View view = getLayoutInflater().inflate(R.layout.del, null);
        EditText ed = view.findViewById(R.id.name);
        String str=ed.getText().toString();

        builder.setView(view);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<itemList.size();i++)
                {
                    if(itemList.get(i).contains(str))
                    {  itemList.remove(i);
                        break;}
                }
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}



