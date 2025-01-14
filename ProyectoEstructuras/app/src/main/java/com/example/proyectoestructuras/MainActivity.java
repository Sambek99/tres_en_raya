package com.example.proyectoestructuras;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectoestructuras.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0}; //9 zero
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        combinationList.add(new int[] {0,1,2});
        combinationList.add(new int[] {3,4,5});
        combinationList.add(new int[] {6,7,8});
        combinationList.add(new int[] {0,3,6});
        combinationList.add(new int[] {1,4,7});
        combinationList.add(new int[] {2,5,8});
        combinationList.add(new int[] {2,4,6});
        combinationList.add(new int[] {0,4,8});

        String getPlayerOneName = "JUGADOR";
        String getPlayerTwoName = "MAQUINA";

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(0)){
                    performAction((ImageView) view, 0);
                }
            }
        });

        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(1)){
                    performAction((ImageView) view, 1);
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(2)){
                    performAction((ImageView) view, 2);
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(3)){
                    performAction((ImageView) view, 3);
                }
            }
        });
        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(4)){
                    performAction((ImageView) view, 4);
                }
            }
        });
        binding.image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(5)){
                    performAction((ImageView) view, 5);
                }
            }
        });
        binding.image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(6)){
                    performAction((ImageView) view, 6);
                }
            }
        });
        binding.image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(7)){
                    performAction((ImageView) view, 7);
                }
            }
        });
        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)){
                    performAction((ImageView) view, 8);
                }
            }
        });

    }

    private void performAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.xicon);
            if (checkResults()) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,  "Jugador es el ganador!", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this, "Empate", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;

                // Turno de la IA (Jugador 2)
                int bestMove = getBestMove();
                if (bestMove != -1) {
                    ImageView bestMoveView = getImageViewForBox(bestMove);
                    performAction(bestMoveView, bestMove);
                }
            }
        } else {
            imageView.setImageResource(R.drawable.oicon);
            if (checkResults()) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,  "La maquina es el Ganador!", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this, "Empate", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }


    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.bordenegro);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.cajablanca);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.bordenegro);
            binding.playerOneLayout.setBackgroundResource(R.drawable.cajablanca);
        }
    }

    private boolean checkResults() {
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] != 0 &&
                    boxPositions[combination[0]] == boxPositions[combination[1]] &&
                    boxPositions[combination[1]] == boxPositions[combination[2]]) {
                return true;
            }
        }
        return false;
    }


    private boolean isBoxSelectable(int boxPosition) {
        boolean response = false;
        if (boxPositions[boxPosition] == 0) {
            response = true;
        }
        return response;
    }

    public void restartMatch(){
        boxPositions = new int[] {0,0,0,0,0,0,0,0,0}; //9 zero
        playerTurn = 1;
        totalSelectedBoxes = 1;

        binding.image1.setImageResource(R.drawable.cajablanca);
        binding.image2.setImageResource(R.drawable.cajablanca);
        binding.image3.setImageResource(R.drawable.cajablanca);
        binding.image4.setImageResource(R.drawable.cajablanca);
        binding.image5.setImageResource(R.drawable.cajablanca);
        binding.image6.setImageResource(R.drawable.cajablanca);
        binding.image7.setImageResource(R.drawable.cajablanca);
        binding.image8.setImageResource(R.drawable.cajablanca);
        binding.image9.setImageResource(R.drawable.cajablanca);
    }

    private int getBestMove() {
        // Prioridad 1: Verificar si la máquina puede ganar
        for (int i = 0; i < boxPositions.length; i++) {
            if (isBoxSelectable(i)) {
                boxPositions[i] = 2;
                if (checkResults()) {
                    boxPositions[i] = 0;
                    return i;
                }
                boxPositions[i] = 0;
            }
        }

        // Prioridad 2: Bloquear al jugador si está a punto de ganar
        for (int i = 0; i < boxPositions.length; i++) {
            if (isBoxSelectable(i)) {
                boxPositions[i] = 1;
                if (checkResults()) {
                    boxPositions[i] = 0;
                    return i; // Bloquea inmediatamente
                }
                boxPositions[i] = 0;
            }
        }

        // Prioridad 3: Usar minimax para encontrar el mejor movimiento
        int bestScore = Integer.MAX_VALUE;
        int bestMove = -1;

        for (int i = 0; i < boxPositions.length; i++) {
            if (isBoxSelectable(i)) {
                boxPositions[i] = 2;
                int score = minimax(false);
                boxPositions[i] = 0;

                if (score < bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }


    private int minimax(boolean isMaximizing) {
        if (checkResults()) {
            return isMaximizing ? -10 : 10; // Si gana el jugador 1: -10, si gana la IA: +10
        }
        if (totalSelectedBoxes == 9) {
            return 0; // Empate
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentPlayer = isMaximizing ? 1 : 2;

        for (int i = 0; i < boxPositions.length; i++) {
            if (isBoxSelectable(i)) {
                boxPositions[i] = currentPlayer; // Simula el movimiento
                totalSelectedBoxes++;

                int score = minimax(!isMaximizing);

                // Incluir utilidad en la evaluación
                score += calculateUtility(currentPlayer);

                boxPositions[i] = 0; // Deshacer el movimiento
                totalSelectedBoxes--;

                bestScore = isMaximizing ? Math.max(bestScore, score) : Math.min(bestScore, score);
            }
        }
        return bestScore;
    }

    private ImageView getImageViewForBox(int boxPosition) {
        switch (boxPosition) {
            case 0: return binding.image1;
            case 1: return binding.image2;
            case 2: return binding.image3;
            case 3: return binding.image4;
            case 4: return binding.image5;
            case 5: return binding.image6;
            case 6: return binding.image7;
            case 7: return binding.image8;
            case 8: return binding.image9;
            default: return null;
        }
    }
    private int calculateUtility(int currentPlayer) {
        int playerScore = 0, opponentScore = 0;
        for (int[] combination : combinationList) {
            int playerCount = 0, opponentCount = 0;

            for (int position : combination) {
                if (boxPositions[position] == currentPlayer) {
                    playerCount++;
                } else if (boxPositions[position] != 0) {
                    opponentCount++;
                }
            }

            if (opponentCount == 0) {
                playerScore++;
            }

            // Si el jugador ocupa una casilla, esa combinación no es utilizable para el oponente
            if (playerCount == 0) {
                opponentScore++;
            }
        }

        // Calcular utilidad: Diferencia entre opciones disponibles
        return playerScore - opponentScore;
    }




}