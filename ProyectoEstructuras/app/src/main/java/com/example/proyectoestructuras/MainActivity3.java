package com.example.proyectoestructuras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectoestructuras.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

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

        String getPlayerOneName = "Jugador 1";
        String getPlayerTwoName = "Jugador 2";

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        binding.image1.setOnClickListener(view -> handlePlayerAction((ImageView) view, 0));
        binding.image2.setOnClickListener(view -> handlePlayerAction((ImageView) view, 1));
        binding.image3.setOnClickListener(view -> handlePlayerAction((ImageView) view, 2));
        binding.image4.setOnClickListener(view -> handlePlayerAction((ImageView) view, 3));
        binding.image5.setOnClickListener(view -> handlePlayerAction((ImageView) view, 4));
        binding.image6.setOnClickListener(view -> handlePlayerAction((ImageView) view, 5));
        binding.image7.setOnClickListener(view -> handlePlayerAction((ImageView) view, 6));
        binding.image8.setOnClickListener(view -> handlePlayerAction((ImageView) view, 7));
        binding.image9.setOnClickListener(view -> handlePlayerAction((ImageView) view, 8));
    }

    private void handlePlayerAction(ImageView imageView, int selectedBoxPosition) {
        if (!isBoxSelectable(selectedBoxPosition)) {
            return;
        }

        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.xicon);
            if (checkResults()) {
                showResultDialog("Jugador 1 es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        } else {
            imageView.setImageResource(R.drawable.oicon);
            if (checkResults()) {
                showResultDialog("Jugador 2 es el ganador!");
            } else if (totalSelectedBoxes == 9) {
                showResultDialog("Empate");
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
        return boxPositions[boxPosition] == 0;
    }

    private void showResultDialog(String message) {
        runOnUiThread(() -> {
            ResultDialog2 resultDialog = new ResultDialog2(MainActivity3.this, message, MainActivity3.this);
            resultDialog.setCancelable(false);
            resultDialog.show();
        });
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

        binding.playerOneLayout.setBackgroundResource(R.drawable.bordenegro);
        binding.playerTwoLayout.setBackgroundResource(R.drawable.cajablanca);
    }
}

