package com.example.proyectoestructuras;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultDialog extends Dialog {

    private final String message;
    private final MainActivity mainActivity;

    private final Context context;

    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.context=context;
        this.message = message;
        this.mainActivity = mainActivity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);

        TextView messageText = findViewById(R.id.messageText);
        messageText.setText(message);

        Button startAgainButton = findViewById(R.id.startAgainButton);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restartMatch();
                dismiss();
            }
        });
        Button backToMenuButton = findViewById(R.id.menuboton);
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPlayers.class);
                context.startActivity(intent);

                // Cierra el diálogo y la actividad actual
                dismiss();
                if (context instanceof Activity) {
                    ((Activity) context).finish(); // Finaliza la actividad actual para limpiar la pila
                }
            }
        });

    }
}