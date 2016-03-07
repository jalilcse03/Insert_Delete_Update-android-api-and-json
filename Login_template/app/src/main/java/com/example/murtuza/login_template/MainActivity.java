package com.example.murtuza.login_template;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void viewproducts(View view){
        Intent intent=new Intent(MainActivity.this,AllProductsActivity.class);
        startActivity(intent);

    }
    public void newaddproducts(View view){
        Intent intent=new Intent(MainActivity.this,NewProductActivity.class);
        startActivity(intent);

    }
}
