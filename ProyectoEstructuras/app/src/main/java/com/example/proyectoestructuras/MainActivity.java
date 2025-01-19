package com.example.proyectoestructuras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectoestructuras.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    protected final List<int[]> combinationList = new ArrayList<>();
    protected int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    protected int playerTurn = 1;
    protected int totalSelectedBoxes = 1;
    protected TreeNode decisionTree;
    protected int turnos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        String getPlayerOneName = "JUGADOR";
        String getPlayerTwoName = "MAQUINA";

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        if (this.getClass() == MainActivity.class) {
            showChoosePlayerDialog();
        }


        if(playerTurn == 2 && turnos == 0){
            makeIATurn();
        }

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


        decisionTree = generateDecisionTree(boxPositions, true);



    }

    public void showChoosePlayerDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Selecciona quién inicia")
                .setMessage("IA o Jugador?")
                .setPositiveButton("IA", (dialog, which) -> {
                    playerTurn = 2; // La IA comienza
                    if (playerTurn == 2 && turnos == 0) {
                        makeIATurn(); // Si la IA es la que inicia, realiza su primer movimiento
                    }
                })
                .setNegativeButton("Jugador", (dialog, which) -> {
                    playerTurn = 1; // El jugador comienza
                })
                .setCancelable(false)
                .show();
    }

    public void performAction(ImageView imageView, int selectedBoxPosition) {
        // Asignamos la jugada del jugador o la IA en el tablero
        boxPositions[selectedBoxPosition] = playerTurn;

        // Si el jugador es el que está jugando
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.xicon); // Marca X
            turnos ++;

            // Verificamos si hay ganador o empate
            if (checkResults()) {
                showResultDialog("Jugador es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
            } else {
                // Cambiamos el turno a la IA
                changePlayerTurn(2);
                totalSelectedBoxes++;

                // Llamamos a la IA para que haga su movimiento
                makeIATurn(); // Realiza la jugada de la IA sin esperar un clic
            }
        } else if (playerTurn == 2) {
            turnos ++;
            // La IA hace su movimiento automáticamente (marca O)
            imageView.setImageResource(R.drawable.oicon);

            // Verificamos si la IA ha ganado o si el juego terminó en empate
            if (checkResults()) {
                showResultDialog("La máquina es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
            } else {
                // Cambiamos el turno al jugador
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    // Método para hacer que la IA haga su movimiento
    public void makeIATurn() {
        // Genera el árbol de decisiones para la IA
        decisionTree = generateDecisionTree(boxPositions, true);

        // Obtén el mejor movimiento de la IA
        int bestMove = decisionTree.bestMove;

        if (bestMove != -1) {
            // Realiza la jugada de la IA en el tablero
            ImageView bestMoveView = getImageViewForBox(bestMove);
            performAction(bestMoveView, bestMove); // Realiza la jugada sin esperar clics
        }
    }



    public void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.bordenegro);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.cajablanca);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.bordenegro);
            binding.playerOneLayout.setBackgroundResource(R.drawable.cajablanca);
        }
    }

    public boolean checkResults() {
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
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
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

        decisionTree = generateDecisionTree(boxPositions, true);
        showChoosePlayerDialog();
        if(playerTurn == 2 && turnos == 0){
            makeIATurn();
        }
    }

    public TreeNode generateDecisionTree(int[] state, boolean isMaximizing) {
        TreeNode root = new TreeNode(state.clone(), -1);
        generateTree(root, isMaximizing, 0);
        return root;
    }

    public void generateTree(TreeNode node, boolean isMaximizing, int depth) {
        if (checkWinner(node.state, 1) || checkWinner(node.state, 2) || isBoardFull(node.state)) {
            node.value = evaluateState(node.state, depth);
            return;
        }

        int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < node.state.length; i++) {
            if (node.state[i] == 0) {
                int[] newState = node.state.clone();
                newState[i] = isMaximizing ? 2 : 1;

                TreeNode child = new TreeNode(newState, i);
                generateTree(child, !isMaximizing, depth + 1);

                node.children.add(child);
                if (isMaximizing) {
                    if (child.value > bestValue) {
                        bestValue = child.value;
                        node.bestMove = i;
                    }
                } else {
                    if (child.value < bestValue) {
                        bestValue = child.value;
                        node.bestMove = i;
                    }
                }
            }
        }
        node.value = bestValue;
    }

    public int evaluateState(int[] state, int depth) {
        if (checkWinner(state, 2)) return 10 - depth;
        if (checkWinner(state, 1)) return depth - 10;
        return 0;
    }

    public boolean checkWinner(int[] state, int player) {
        for (int[] combination : combinationList) {
            if (state[combination[0]] == player &&
                    state[combination[1]] == player &&
                    state[combination[2]] == player) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardFull(int[] state) {
        for (int position : state) {
            if (position == 0) return false;
        }
        return true;
    }

    public ImageView getImageViewForBox(int boxPosition) {
        switch (boxPosition) {
            case 0:
                return binding.image1;
            case 1:
                return binding.image2;
            case 2:
                return binding.image3;
            case 3:
                return binding.image4;
            case 4:
                return binding.image5;
            case 5:
                return binding.image6;
            case 6:
                return binding.image7;
            case 7:
                return binding.image8;
            case 8:
                return binding.image9;
            default:
                return null;
        }
    }

    public void showResultDialog(String message) {
        runOnUiThread(() -> {
            try {
                Log.d("DEBUG", "Mostrando el diálogo: " + message);
                if (!isFinishing() && !isDestroyed()) {
                    ResultDialog resultDialog = new ResultDialog(MainActivity.this, message, MainActivity.this);
                    resultDialog.setCancelable(false);
                    resultDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}