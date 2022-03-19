package com.example.icp8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() { //when we click on logout button
            @Override
            public void onClick(View view) { //It goes from second page to the login page
                Intent redirect = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(redirect);
            }
        });
    }
}