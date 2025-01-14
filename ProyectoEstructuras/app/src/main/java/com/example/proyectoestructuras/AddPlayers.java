package com.example.proyectoestructuras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddPlayers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        Button jvj = findViewById(R.id.pvia);
        Button jvmaquina = findViewById(R.id.pvp);

        jvj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                startActivity(intent);
            }
        });
        jvmaquina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                startActivity(intent);
            }
        });
        

    }
}