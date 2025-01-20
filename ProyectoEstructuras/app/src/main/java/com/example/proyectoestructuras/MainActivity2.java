package com.example.proyectoestructuras;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //finishes=true;
        super.onCreate(savedInstanceState);
        binding.playerOneName.setText("IA 1");
        binding.playerTwoName.setText("IA 2");
        decisionTree = generateDecisionTree(boxPositions, true);
        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)) {
                    performAction((ImageView) view, 8);
                }
            }
        });
        startAIvsAIGame();
    }

    private void startAIvsAIGame() {
        playerTurn=1;
        new Thread(() -> {
            while (!isGameOver()) {
                runOnUiThread(() -> {
                    int bestMove = decisionTree.bestMove;
                    if (bestMove != -1) {
                        ImageView bestMoveView = getImageViewForBox(bestMove);
                        performAIAction(bestMoveView, bestMove);
                    }
                });

                try {
                    Thread.sleep(1000); // Pausa para simular tiempo entre turnos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void performAIAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.xicon);
            if (checkResults()) {
                showResultDialog("IA 1 es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;

                // Genera el árbol de decisiones para IA 2
                decisionTree = generateDecisionTree(boxPositions, true);
            }
        } else {
            imageView.setImageResource(R.drawable.oicon);
            if (checkResults()) {
                showResultDialog("IA 2 es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;

                // Genera el árbol de decisiones para IA 1
                decisionTree = generateDecisionTree(boxPositions, true);
            }
        }
    }

    private boolean isGameOver() {
        return /*checkResults() ||*/ totalSelectedBoxes == 9;
    }

    public void showResultDialog(String message) {
        runOnUiThread(() -> {
            try {
                Log.d("DEBUG", "Mostrando el diálogo: " + message);
                if (!isFinishing() && !isDestroyed()) {
                    ResultDialogMenu resultDialog = new ResultDialogMenu(MainActivity2.this, message, MainActivity2.this);
                    resultDialog.setCancelable(false);
                    resultDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
