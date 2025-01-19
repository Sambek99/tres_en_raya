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

public class ResultDialogMenu extends Dialog {

    private final String message;
    private final MainActivity2 mainActivity;
    private final Context context;


    //Context miContext;

    public ResultDialogMenu(@NonNull Context context, String message, MainActivity2 mainActivity) {
        super(context);
        this.context=context;
        this.message = message;
        this.mainActivity = mainActivity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog2);

        TextView messageText = findViewById(R.id.messageText2);
        messageText.setText(message);

        Button backToMenuButton = findViewById(R.id.menuboton2);
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