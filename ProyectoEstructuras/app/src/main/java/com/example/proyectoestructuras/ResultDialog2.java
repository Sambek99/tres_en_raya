package com.example.proyectoestructuras;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultDialog2 extends Dialog {

    private final String message;
    private final MainActivity3 mainActivity;
    private final Context context;


    //Context miContext;

    public ResultDialog2(@NonNull Context context, String message, MainActivity3 mainActivity) {
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

                dismiss();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

    }
}