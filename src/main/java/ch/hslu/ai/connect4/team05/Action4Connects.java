package ch.hslu.ai.connect4.team05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.function.Function;

import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.team05.PatternMatcher.Position;

public class Action4Connects {

    private final Node root;
    private final int maxDeph;
    private final char[][] board;
    private final char player;
    private final char otherPlayer;
    public final Function<char[][], Double> heuristicFunction;
    
    private static final Double inf = Double.POSITIVE_INFINITY;
    private static final int DEFAULT_MAXDEPH = 3; 

    public static class Node {
        private final boolean maximizingPlayer;
        private final Node parent;
        private final char[][] board;

        private Double alpha = inf;
        private Double beta = -inf;

        private Position changed;

        private Double value;
        private int action = -1;

        private Node(char[][] board, Node parent, Boolean maximizingPlayer) {
            this.board = board;
            this.parent = parent;
            this.maximizingPlayer = maximizingPlayer;

            if(parent != null) {
                this.alpha = parent.alpha;
                this.beta = parent.beta;
            }
            this.value = maximizingPlayer ? -inf : inf;
        }

        public void print() {
            System.out.println(this.toString());
            printBoard(this.board);
        }

        @Override
        public String toString() {
            return new StringBuilder()
                .append("[ ")
                .append("maximizingPlayer: ").append(maximizingPlayer)
                .append("parent: ").append(parent)
                .append("alpha: ").append(alpha)
                .append("beta: ").append(beta)
                .append("value: ").append(value)
                .append("action: ").append(action)
                .append(" ]")
                .toString();
        }
    }

    public Action4Connects(char[][] board, Function<char[][], Double> heuristicFunction, char player) {
        this(board, heuristicFunction, player, DEFAULT_MAXDEPH);
    }

    /**
     * @param board Board of the game
     * @param heuristicFunction Determen value of the boards status for the given player, if higher then better 1 = won ; -1 = lost; 
     *                          anything between is better for player or worse depending on value.
     * @param player the symbol of the player on the board
     */
    public Action4Connects(char[][] board, Function<char[][], Double> heuristicFunction, char player, int maxDeph) {
        this.maxDeph = maxDeph;
        this.heuristicFunction = heuristicFunction;
        this.player = player;
        this.board = board;
        this.otherPlayer = getOtherPlayer(board, player);

        this.root = new Node(board, null, true);
    }

    public static Position getNewPosition(final int column, final char[][] board) {
        if(board[column][0] != Game.EMPTY) {
            throw new IndexOutOfBoundsException();
        }

        int row = board[column].length - 1;
        for(; board[column][row] != Game.EMPTY; row--);
        return new Position(column, row);
    }

    public static char[][] addToPosition(final Position pos, final char[][] board, final char symbol) {
        char[][] newBoard = copyBoard(board);
        newBoard[pos.Column][pos.Row] = symbol;
        return newBoard;
    }

    public static char[][] copyBoard(char[][] board) {
        return cutBoard(board, 0, board.length - 1, 0, board[0].length - 1);
    }

    public static char[][] cutBoard(char[][] board, int fromColumn, int toColumn, int fromRow, int toRow) {
        final char[][] result = new char[toColumn - fromColumn + 1][];
        for (int i = fromColumn; i <= toColumn; i++) {
            result[i - fromColumn] = Arrays.copyOfRange(board[i], fromRow, toRow + 1);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

    public static boolean columnIsFull(final char[][] board, final int column) {
        return board[column][0] != (Game.EMPTY);
    }

    /**
     * Checks if given simbol has a 4 in a row on the board
     * @param board the new board
     * @param changed The changed position used so only this change has the be the winning move 
     * @param player the symbol to check if won
     */
    public static boolean playerWins(char[][] board, Position changed, char player) {
        // check vertical
        int fromColumn = changed.Column - 3 > 0 ? changed.Column - 3 : 0;
        int toColumn = changed.Column + 3 < board.length ? changed.Column + 3 : board.length - 1;
        int fromRow = changed.Row - 3 > 0 ? changed.Row - 3 : 0;
        int toRow = changed.Row + 3 < board[0].length ? changed.Row + 3 : board[0].length;

        char[][] subboard = cutBoard(board, fromColumn, toColumn, fromRow, toRow);

        int matches = 0;
        matches += new PatternMatcher(PatternMatcher.DIAGONAL_LEFT_RIGHT_LINE, player).match(subboard);
        matches += new PatternMatcher(PatternMatcher.DIAGONAL_RIGHT_LEFT_LINE, player).match(subboard);
        matches += new PatternMatcher(PatternMatcher.HORIZONTAL_LINE, player).match(subboard);
        matches += new PatternMatcher(PatternMatcher.VERTICAL_LINE, player).match(subboard);

        return matches > 0;
    }

    private Node getNode() {
        if(root.action < 0) {
            alphaBetaPruning(root);
        }
        return root;
    }

    public Double getValue() {
        return getNode().value;
    }

    public int getAction() {
        return getNode().action;
    }

    /**
     * Find other player on the board
     */
    private static char getOtherPlayer(char[][] board, char player) {
        for(char[] column : board) {
            for(char cell : column) {
                if(cell != '-' && cell != player) return cell;
            }
        }
        return (char)(player + 1 < 'z' ? player + 1 : player - 1);
    }


    private Double alphaBetaPruning(final Node node) {
        return alphaBetaPruning(node, 0);
    }

    public static void printBoard(char[][] board) {
        System.out.println("#################");
        for (char[] row : board) {
            System.out.println(new String(row));
        }
        System.out.println("#################");
    }

    private Double alphaBetaPruning(final Node node, final int deph) {
        // finish current tree if current node is winner or max deph reached
        // the parent node can not be a winner, since game would not have started
        boolean playerHasWon = node.parent != null ? playerWins(node.board, node.changed, player) : false;
        if(playerHasWon) { 
            return node.value = !node.maximizingPlayer ? 1.0 : -1.0;
        } else if(deph >= maxDeph) {
            return node.value = heuristicFunction.apply(node.board);
        }

        // extend tree with possible actions and return best value
        for(int column = 0; column < board.length; column++) {
            if(!columnIsFull(node.board, column)) {
                Position change = getNewPosition(column, board);
                Node child = new Node(addToPosition(change, board, player), node, !node.maximizingPlayer);
                child.changed =  change;
                child.action = column;
                child.value = alphaBetaPruning(child, deph + 1);

                if(
                    (node.maximizingPlayer && child.value > node.value) ||
                    (!node.maximizingPlayer && child.value < node.value)
                ) {
                    node.value = child.value;
                    node.action = child.action;
                }
            }
        }

        return node.value;
    }
}