package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ChangeData extends AppCompatActivity implements View.OnClickListener {

    EditText resCh, linkCh, passCh;
    Button saveSh;
    DBHelper dbHelper;
    SQLiteDatabase database;
    int index;
    String r, l, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        setTitle("Редактирование");

        resCh = findViewById(R.id.et_name_of_res_change);
        linkCh = findViewById(R.id.et_link_change);
        passCh = findViewById(R.id.et_pass_change);
        saveSh = findViewById(R.id.save_button_change);
        saveSh.setOnClickListener(this);
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        r = intent.getStringExtra("res");
        l = intent.getStringExtra("link");
        p = intent.getStringExtra("pass");
        index = intent.getIntExtra("ind", 0);
        resCh.setText(r);
        linkCh.setText(l);
        passCh.setText(p);
    }

    @Override
    public void onClick(View v) {
        database = dbHelper.getWritableDatabase();
        String strSQL = "UPDATE " + InputData.TaskEntry.TABLE + " SET " + InputData.TaskEntry.NAMES + " = " + "'" + resCh.getText().toString() + "' , " + InputData.TaskEntry.LINKS + " = " + "'" + linkCh.getText().toString() + "' , " + InputData.TaskEntry.PASSES + " = " + "'" + passCh.getText().toString() + "'" + " WHERE " + InputData.TaskEntry._ID + " = " + index + ";";
        database.execSQL(strSQL);
        database.close();
        Intent intent = new Intent();
        intent.putExtra("r1", resCh.getText().toString());
        intent.putExtra("l1", linkCh.getText().toString());
        intent.putExtra("p1", passCh.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }
}