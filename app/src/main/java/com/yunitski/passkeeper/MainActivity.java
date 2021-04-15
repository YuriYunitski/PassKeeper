package com.yunitski.passkeeper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayList<String> linksArray;
    ArrayList<String> passesArray;
    ArrayList<Integer> indexes;
    ArrayAdapter<String> arrayAdapter;
    public static final int ACCESS_SAVE = 1;
    public static final int DB_CHANGE = 2;
    String res, link, pass, resIn, linkIn, passIn;
    DBHelper dbHelper;
    FragmentManager fragmentManager;
    SQLiteDatabase database;
    FloatingActionButton fab;
    PassInfoFragment passInfoFragment;
    FragmentTransaction fragmentTransaction;
    public static boolean isFragActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resIn = arrayList.get(position);
                linkIn = linksArray.get(position);
                passIn = passesArray.get(position);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                passInfoFragment = new PassInfoFragment(resIn, linkIn, passIn);
                fragmentTransaction.add(R.id.frame, passInfoFragment).commit();
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        isFragActive = false;
        dbHelper = new DBHelper(this);
        updateUI();
        registerForContextMenu(listView);
    }


    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(getApplicationContext(), NewPass.class), ACCESS_SAVE);
        if (isFragActive) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(passInfoFragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESS_SAVE) {
            if (resultCode == RESULT_OK) {
                res = data.getStringExtra("res");
                link = data.getStringExtra("link");
                pass = data.getStringExtra("pass");
                NewPassClass newPassClass = new NewPassClass(res, link, pass);
                String r = newPassClass.getNameOfResource();
                arrayAdapter.add(r);
            }
        } else if (requestCode == DB_CHANGE){
            if (resultCode == RESULT_OK){
                res = data.getStringExtra("r1");
                link = data.getStringExtra("l1");
                pass = data.getStringExtra("p1");

            }
        }
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Clean");
        menu.add(0, 1, 0, "Change Password");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            this.deleteDatabase(InputData.DB_NAME);
            if (isFragActive) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(passInfoFragment).commit();
            }
            updateUI();
        } else if (item.getItemId() == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Change code");
            builder.setMessage("New Code:");
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_to_change_pass, null);
            builder.setView(view);
            EditText editText = (EditText) view.findViewById(R.id.tv_code_change);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (editText.length() == 4) {
                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences(Enter.PASS_C, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(Enter.PASS_C, Integer.parseInt(editText.getText().toString()));
                        editor.apply();
                    } else {
                        Toast.makeText(getApplicationContext(), "too short", Toast.LENGTH_SHORT).show();

                    }
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                deleteFromBase(info.position);
                break;
            case R.id.change_smth:
                Intent intent = new Intent(getApplicationContext(), ChangeData.class);
                String r = arrayList.get(info.position);
                String l = linksArray.get(info.position);
                String p = passesArray.get(info.position);
                int i = indexes.get(info.position);
                intent.putExtra("res", r);
                intent.putExtra("link", l);
                intent.putExtra("pass", p);
                intent.putExtra("ind", i);
                startActivityForResult(intent, DB_CHANGE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updateUI() {
        arrayList = new ArrayList<>();
        linksArray = new ArrayList<>();
        passesArray = new ArrayList<>();
        indexes = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(InputData.TaskEntry.TABLE, new String[]{InputData.TaskEntry._ID, InputData.TaskEntry.NAMES, InputData.TaskEntry.LINKS, InputData.TaskEntry.PASSES}, null, null, null, null, null);
        while (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndex(InputData.TaskEntry._ID);
            int index = cursor.getColumnIndex(InputData.TaskEntry.NAMES);
            int indexL = cursor.getColumnIndex(InputData.TaskEntry.LINKS);
            int indexP = cursor.getColumnIndex(InputData.TaskEntry.PASSES);
            arrayList.add(0, cursor.getString(index));
            linksArray.add(0, cursor.getString(indexL));
            passesArray.add(0, cursor.getString(indexP));
            indexes.add(0, cursor.getInt(idIndex));
        }
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<>(this,  android.R.layout.simple_expandable_list_item_1,
                    arrayList);
            listView.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.clear();
            arrayAdapter.addAll(arrayList);
            arrayAdapter.notifyDataSetChanged();
        }

        cursor.close();
        database.close();
    }
    private void deleteFromBase(int id){
        database = dbHelper.getWritableDatabase();
        String i = indexes.get(id).toString();
        database.delete(InputData.TaskEntry.TABLE, InputData.TaskEntry._ID+"=?", new String[] {i});
        database.close();
        updateUI();
    }
}
