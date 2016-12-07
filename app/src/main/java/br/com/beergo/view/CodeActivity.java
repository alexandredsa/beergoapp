package br.com.beergo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import br.com.beergo.R;

public class CodeActivity extends AppCompatActivity {
    private EditText txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        txtCode = (EditText) findViewById(R.id.code);


    }

    public void send(View v) {

    }
}
