import ch.hslu.ai.connect4.Player;


public class PlayerForTeam05 extends Player{

    public PlayerForTeam05(){
        super("Player for Team05");
    }

    /**
     * The following method allows you to implement your own game intelligence.
     * At the moment, this is a dumb random number generator.
     * The method must return the column number where the computer player puts the next disc.
     * board[i][j] = cell content at position (i,j), i = column, j = row
     *
     * If board[i][j] = this.getSymbol(), the cell contains one of your discs
     * If board[i][j] = '-', the cell is empty
     * Otherwise, the cell contains one of your opponent's discs
     * @param board The current game board
     * @return The columns number where you want to put your disc
     */

    @Override
    public int play(char[][] board) {
        return 0;
    }

    private int evaluateNode(int column, char[][] board){ return 0;}

    private int alphabeta(int column, char[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || columnIsFull(board, column)) {
            return evaluateNode(column, board);
        }
        if (maximizingPlayer){
            for (int i = 0; i < board.length; i++){
                alpha = Math.max(alpha, alphabeta(i, board, depth-1, alpha, beta, false));
                if (beta <= alpha){
                    break;
                }
            return alpha;
            }
        }
        else {
            for (int i = 0; i < board.length; i++){
                beta = Math.min(beta, alphabeta(i, board, depth-1, alpha, beta, true));
                if (beta <= alpha){
                    break;
                }
            }
            return beta;
        }
        return 0;
    }

    private boolean columnIsFull(final char[][] board, final int column){
        return false;
    }
}
