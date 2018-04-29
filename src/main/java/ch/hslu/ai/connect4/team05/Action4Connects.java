package ch.hslu.ai.connect4.team05;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Action4Connects {

    private Node root;
    private int maxDeph;
    
    private static final Double inf = Double.POSITIVE_INFINITY;

    private static final int DEFAULT_MAXDEPH = 3; 

    public static Function<char[][], Integer> heuristicFunction;
    
    public static class Node {
        private Double value;
        private Double alpha = inf;
        private Double beta = -inf;

        private Node parent;
        private List<Node> children;
    }

    public Action4Connects(char[][] board, Function<char[][], Integer> heuristicFunction) {
        this(board, heuristicFunction, DEFAULT_MAXDEPH);
    }

    public Action4Connects(char[][] board, Function<char[][], Integer> heuristicFunction, int maxDeph) {
        this.maxDeph = maxDeph;
        this.heuristicFunction = heuristicFunction;
    }

    public int getTopValue() {
        return 0;
    }
}