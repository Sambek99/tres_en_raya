package com.example.proyectoestructuras;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity2 extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.playerOneName.setText("IA 1");
        binding.playerTwoName.setText("IA 2");

        // Inicia el juego IA vs IA
        decisionTree = generateDecisionTree(boxPositions, true);
        startAIvsAIGame();
    }

    private void startAIvsAIGame() {
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
        return checkResults() || totalSelectedBoxes == 9;
    }
}
