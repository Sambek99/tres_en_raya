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
        Button jvj = findViewById(R.id.pvp);
        Button jvmaquina = findViewById(R.id.pvia);
        Button iavsia = findViewById(R.id.ia_vs_ia);
        jvmaquina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(AddPlayers.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        iavsia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(AddPlayers.this, MainActivity2.class);
                startActivity(intent2);
            }
        });

        jvj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent3 = new Intent(AddPlayers.this, MainActivity3.class);
                startActivity(intent3);
            }
        });

    }

}