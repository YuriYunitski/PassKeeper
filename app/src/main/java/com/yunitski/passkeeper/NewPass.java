package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPass extends AppCompatActivity implements View.OnClickListener {

    EditText res, name, link, pass;
    Button save;
    String resToS, nameToS, linkToS, passToS;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        setTitle("Новый пароль");

        res = findViewById(R.id.et_name_of_res);
        name = findViewById(R.id.et_name);
        link = findViewById(R.id.et_link);
        pass = findViewById(R.id.et_pass);
        save = findViewById(R.id.save_button);
        save.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        resToS = res.getText().toString();
        nameToS = name.getText().toString();
        linkToS = link.getText().toString();
        passToS = pass.getText().toString();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!resToS.isEmpty() || !linkToS.isEmpty() || !passToS.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("res", resToS);
            intent.putExtra("name", nameToS);
            intent.putExtra("link", linkToS);
            intent.putExtra("pass", passToS);
            setResult(RESULT_OK, intent);
            cv.put(InputData.TaskEntry.NAMES, resToS);
            cv.put(InputData.TaskEntry.NAMESTR, nameToS);
            cv.put(InputData.TaskEntry.LINKS, linkToS);
            cv.put(InputData.TaskEntry.PASSES, passToS);
            db.insert(InputData.TaskEntry.TABLE, null, cv);
            db.close();
            finish();
        }
    }
}
