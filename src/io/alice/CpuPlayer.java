package io.alice;

public class CpuPlayer extends APlayer {
    /**
     * Constructor for objects of class CpuPlayer
     *
     * @params game the tic-tac-toe game that is to be played
     * @params the character symbol to be used to represent this player's moves
     */
    CpuPlayer(Game game, char symbol) {
        super(game, symbol);
    }

    /**
     * Picks a move uniformly at random. It does this by generating random moves within the game board boundaries until
     * it finds an unnocupied position. Assumes the game isn't over yet, otherwise it'd go into an infinite loop.
     *
     * @return the chosen move. Because the CPU never quits, this implementation method never returns null.
     */
    public Move pickMove() {
        int boardSize = game.getBoardSize();
        Move move = new Move(0, 0);
        do {
            move.row = (int) (Math.random() * (double) boardSize);
            move.col = (int) (Math.random() * (double) boardSize);
        } while (this.game.isValidMove(move) != 'V');
        int cRow = (char) (move.row + 65);            // cRow return the row of the computer's move
        int cCol = move.col + 1;                     // cCol return the column of the computer's move
        System.out.printf("CPU's move: %c%d\n", cRow, cCol);
        return move;
    }
}
