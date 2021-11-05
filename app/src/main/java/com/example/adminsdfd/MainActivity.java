package com.example.adminsdfd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

        Button mainpopular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainpopular=findViewById(R.id.btn_mainpopular);
        mainpopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PopularActivity.class);
                startActivity(intent);
            }
        });
    }
}