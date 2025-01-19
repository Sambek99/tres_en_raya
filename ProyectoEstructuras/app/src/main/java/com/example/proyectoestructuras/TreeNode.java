package com.example.proyectoestructuras;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
        int[] state;
        int value;
        int bestMove;
        List<TreeNode> children;

        TreeNode(int[] state, int move) {
            this.state = state;
            this.bestMove = move;
            this.children = new ArrayList<>();
        }

}
