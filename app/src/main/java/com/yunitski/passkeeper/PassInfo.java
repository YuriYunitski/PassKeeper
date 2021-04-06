package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class PassInfo extends AppCompatActivity {

    EditText res, link, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);

        res = findViewById(R.id.etResInfo);
        link = findViewById(R.id.etLinkInfo);
        pass = findViewById(R.id.etPassInfo);

        Intent intent = getIntent();
        String r = intent.getStringExtra("resIn");
        String l = intent.getStringExtra("linkIn");
        String p = intent.getStringExtra("passIn");
        res.setText(r);
        link.setText(l);
        pass.setText(p);
    }
}