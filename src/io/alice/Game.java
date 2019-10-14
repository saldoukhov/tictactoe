package io.alice;
import io.alice.Move;
import io.alice.APlayer;
import java.io.IOException;

/**
 * Game class which represents the Tic-Tac-Toe game
 * @author Alice Aldoukhov
 * @version v1
 * */
public class Game {
    /** represents the game board as matrix of player symbols */
    private char[][] board;
    /** represents board size, which will be a boardSize x boardSize matrix */
    final int boardSize;
    /** represents the game board as matrix of player symbols */
    private APlayer[] players;
    /** the character to be used to represent a blank space on the board (' ') */
    private char SYMBOL_BLANK = ' ';
    /** the character to be used to represent a cpu move on the board ('O') */
    private char SYMBOL_CPU = 'O';
    /** the character to be used to represent a human move on the board ('X') */
    private char SYMBOL_HUMAN = 'X';

    /**
     * Constructor for objects of class Game
     */
    public Game(int boardSize) {
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.players = new APlayer[2];
        this.players[0] = new HumanPlayer(this, 'X');
        this.players[1] = new CpuPlayer(this, 'O');
    }

    /**
     * Returns the game board size
     */
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Resets the game state so we can play again.
     */
    protected void resetGame() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                this.board[row][col] = ' ';
            }
        }
    }

    /**
     * Validates a potential move. Returns 'V' if the move is valid,
     * or a different character indicating the reason why the move is invalid.
     *
     * @param move the move to be validated
     * @return 'V; the move is valid, 'R' invalid row, 'C" invalid column, 'O' if position is occupied
     */
    public char isValidMove(Move move) {
        char isVal = 'V';
        if (move.row >= 0 && move.row < this.boardSize) {
            if (move.col >= 0 && move.col < this.boardSize) {
                if (this.board[move.row][move.col] != ' ') {
                    isVal = 'O';        // returns 'O' if the position is already occupied
                }
            } else {
                isVal = 'C';            // returns 'C' if invalid column is selected
            }
        } else {
            isVal = 'R';                // returns 'R' if invalid row is selected
        }
        return isVal;
    }

    /**
     * Executes the move passed as an argument. If the move is invalid, returns false
     *
     * @param move   the move to be executed
     * @param symbol the symbol of the player who is making the move
     * @return true if the move was successfully executed
     */
    protected boolean executeMove(Move move, char symbol) {
        boolean isMoveValid = false;
        if (this.isValidMove(move) == 'V') {
            this.board[move.row][move.col] = symbol;
            isMoveValid = true;
        }
        return isMoveValid;
    }

    /**
     * A method that analyzes the board to determine the current game state, which is then returned as a character.
     * A game is over if either player has completed a row, a line, or a diagonal.
     * A game is also over if the board is full, even if no player completed a row, line, or diagonal. That's a tie.
     *
     * @return A character indicating the game state: '?' if the game isn't over yet, 'T' if the game is over and tied,
     * or, if a player won, the winning player's symbol ('X' or 'O').
     */
    public char getGameStatus() {
        char gameRes = '?';
        boolean isGameOver = false;
        boolean isBoardFull = true;
        boolean isRowDone = false;
        boolean isColDone = false;
        boolean isDiagDone = false;
        char symb; // the first symbol in the row/column/diagonal against which the others are compared
        int row;
        int col;

        for(row = 0; row < this.boardSize; row++) {
            symb = this.board[row][0];
            if (this.board[row][0] != ' ')
                isRowDone = true;
            for (col = 0; col < this.boardSize; col++) {
                if (this.board[row][col] == ' ')
                    isBoardFull = false;
                if (this.board[row][col] != symb)
                    isRowDone = false;
            }
            if (isRowDone && !isGameOver) {
                isGameOver = true;
                gameRes = symb;
            }
        }

        for(col = 0; col < this.boardSize; col ++) {
            symb = this.board[0][col];
            if (this.board[0][col] != ' ')
                isColDone = true;
            for (row = 0; row < this.boardSize; row++) {
                if (this.board[row][col] == ' ')
                    isBoardFull = false;
                if (this.board[row][col] != symb)
                    isColDone = false;
            }
            if (isColDone && !isGameOver) {
                isGameOver = true;
                gameRes = symb;
            }
        }

        // Checks if the top left to bottom right diagonal has been completed
        symb = this.board[0][0];
        col = 1;
        if(symb != ' ' && !isGameOver)
            isDiagDone = true;

        for(row = 1; row < boardSize && col < this.boardSize; row++){
            if (this.board[row][col] != symb) {
                isDiagDone = false;
                break;
            }
            col ++;
            }

        if(isDiagDone){
            isGameOver = true;
            gameRes = symb;
        }

        // Checks if the bottom right to top left diagonal has been completed
        symb = this.board[boardSize - 1][0];
        col = 0;
        if(symb != ' ' && !isGameOver)
            isDiagDone = true;

        for(row = boardSize - 1; row >= 0 && col < this.boardSize; row--){
            if (this.board[row][col] != symb) {
                isDiagDone = false;
            }
            col ++;
        }

        if(isDiagDone){
            isGameOver = true;
            gameRes = symb;
        }
        // If the board is full and none of the previous winning conditions have been fulfilled, returns 'T' for tie.
        if (isBoardFull && !isGameOver){
            gameRes = 'T';
        }

        return gameRes;
    }


    /**
     * Creates a textual representation of the game board
     * @return A String representing the game board in the aforementioned format.
     * */
    public String toString () {
        String bs = "    ";         // bs stands for board string
        for (int col = 0; col <= (this.boardSize - 1); col++) {
            bs = bs + (col + 1) + " ";
            if (col < this.boardSize - 1) {
                bs = bs + "  ";
            }
        }
        bs = bs + "\n";
        char rowHead = 'A';
        for (int row = 0; row <= (this.boardSize - 1); row++) {
            bs = bs + " " + rowHead++ + " ";
            for (int col = 0; col < this.boardSize; ++col) {
                bs = bs + " " + this.board[row][col];
                if (col < this.boardSize - 1) {
                    bs = bs + " |";
                }
            }
            bs = bs + "\n";
            if (row < this.boardSize - 1) {
                bs = bs + "   ";
                for (int col = 0; col < this.boardSize; col++) {
                    bs = bs + "---";
                    if (col < this.boardSize - 1) {
                        bs = bs + "|";
                    }
                }
            }
            bs = bs + "\n";
        }
        return bs;
    }

    /**
     * Plays a single game of Tic-tac-toe by having players pick moves in turn.
     * The first player to play is choosen uniformly at random.
     * @return A character representing the game's result: 'H' if the human player won, 'C' if the CPU won, '
     * T' if there was a tie, or 'Q' if the human quit the game.
     * */
    public char playSingleGame () {
        this.resetGame();
        System.out.println("\n---------- NEW GAME ----------\n");
        System.out.println(this);
        int turn = (int)(2 * Math.random());
        char res;
        do {
            Move move = this.players[turn].pickMove();
            if (move == null) {
                return 'Q';
            }
            this.executeMove(move, this.players[turn].getSymbol());
            System.out.println(this);
            if (turn == 0){  // changes turns from one player to the other
                turn = 1;
            } else turn = 0;
            res = this.getGameStatus();
            switch(res) {
                case 'O':
                    res = 'C';
                    break;
                case 'X':
                    res = 'H';
            }
        } while(res == '?');
        return res;
    }

    /**
     * Runs consecutive Tic-tac-toe games until the user gets tired and quits.
     * When the user quits, the win-loss-tie statistics are printed.
     * @params args The first argument represents the desired game board size, which should be an integer in [1,9].
     * If the provided board size does not comply with these rules or if no argument is provided,
     * a default game board size of 3 x 3 will be used.
     * */
    public static void main (String[]args) {
        System.out.println("Tic-Tac-Toe by Alice Aldoukhov\n");
        int boardSize = 3;
        if (args.length > 0) {
            try {
                boardSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid board size '%s' (not an integer), using default size of 3 instead.\n", args[0]);
            }
            if (boardSize > 9 || boardSize < 1) {
                System.out.printf("Specified board size '%s' out of range [1,9], using default size of 3 instead.\n", args[0]);
                boardSize = 3;
            }
        }

        Game newGame = new Game(boardSize);
        GameStats gStats = new GameStats() {};
        boolean keepPlaying = true;
        do {
            char res = newGame.playSingleGame();
            switch (res) {
                case 'H':
                    System.out.println("You won! Play again?");
                    gStats.recordWin();
                    break;
                case 'C':
                    System.out.println("You lost... Play again?");
                    gStats.recordLoss();
                    break;
                case 'T':
                    System.out.println("It's a tie. Play again?");
                    gStats.recordTie();
                    break;
                case 'Q':
                    keepPlaying = false;
            }
        } while (keepPlaying);
        System.out.printf("%s\nGoodbye!\n", gStats);
    }
}