import ch.hslu.ai.connect4.Player;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerForTeam05Test {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private Player player;
    private char[][] board;

    @BeforeClass
    public void setup() {
        this.player = new PlayerForTeam05();
        this.board = new char[COLUMNS][ROWS];
    }
}
