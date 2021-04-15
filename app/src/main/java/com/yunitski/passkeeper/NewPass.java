package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPass extends AppCompatActivity implements View.OnClickListener {

    EditText res, link, pass;
    Button save;
    String resToS, linkToS, passToS;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        setTitle("Новый пароль");

        res = findViewById(R.id.et_name_of_res);
        link = findViewById(R.id.et_link);
        pass = findViewById(R.id.et_pass);
        save = findViewById(R.id.save_button);
        save.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        resToS = res.getText().toString();
        linkToS = link.getText().toString();
        passToS = pass.getText().toString();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!resToS.isEmpty() || !linkToS.isEmpty() || !passToS.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("res", resToS);
            intent.putExtra("link", linkToS);
            intent.putExtra("pass", passToS);
            setResult(RESULT_OK, intent);
            cv.put(InputData.TaskEntry.NAMES, resToS);
            cv.put(InputData.TaskEntry.LINKS, linkToS);
            cv.put(InputData.TaskEntry.PASSES, passToS);
            db.insert(InputData.TaskEntry.TABLE, null, cv);
            db.close();
            finish();
        }
    }
}
