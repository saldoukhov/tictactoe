package io.alice;

import java.util.HashMap;
import java.util.Map;

/**
 * Game class which represents the Tic-Tac-Toe game
 *
 * @author Alice Aldoukhov
 * @version v1
 */
public class Game {
    /**
     * represents the game board as matrix of player symbols
     */
    private char[][] board;
    /**
     * represents board size, which will be a boardSize x boardSize matrix
     */
    private final int boardSize;
    /**
     * represents the game board as matrix of player symbols
     */
    private APlayer[] players;

    /**
     * Constructor for objects of class Game
     */
    private Game(int boardSize) {
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.players = new APlayer[2];
        this.players[0] = new HumanPlayer(this, 'X');
        this.players[1] = new CpuPlayer(this, 'O');
    }

    /**
     * Returns the game board size
     */
    int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Resets the game state so we can play again.
     */
    private void resetGame() {
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
    char isValidMove(Move move) {
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
     */
    private void executeMove(Move move, char symbol) {
        if (this.isValidMove(move) == 'V') {
            this.board[move.row][move.col] = symbol;
        }
    }

    /**
     * @param line row, column or diagonal to check
     * @return ' ' if incomplete, ? if mix, symbol if complete with single symbol
     */
    private static char getLineStatus(char[] line) {
        char result = line[0];
        if (result == ' ')
            return ' ';
        for (int i = 1; i < line.length; i++) {
            char current = line[i];
            if (current == ' ')
                return ' ';
            if (current != result) {
                result = '?';
            }
        }
        return result;
    }

    private static void countLine(char lineResult, Map<Character, Integer> lineCounts) {
        Integer existingCount = lineCounts.getOrDefault(lineResult, 0);
        lineCounts.put(lineResult, existingCount + 1);
    }

    private char[] getColumn(int column) {
        char[] line = new char[this.boardSize];
        for (int row = 0; row < this.boardSize; row++) {
            line[row] = this.board[row][column];
        }
        return line;
    }

    private char[] getMajorDiag() {
        char[] line = new char[this.boardSize];
        for (int row = 0; row < this.boardSize; row++) {
            line[row] = this.board[row][row];
        }
        return line;
    }

    private char[] getSubDiag() {
        char[] line = new char[this.boardSize];
        for (int row = 0; row < this.boardSize; row++) {
            line[row] = this.board[row][this.boardSize - row - 1];
        }
        return line;
    }

    /**
     * A method that analyzes the board to determine the current game state, which is then returned as a character.
     * A game is over if either player has completed a row, a line, or a diagonal.
     * A game is also over if the board is full, even if no player completed a row, line, or diagonal. That's a tie.
     *
     * @return A character indicating the game state: '?' if the game isn't over yet, 'T' if the game is over and tied,
     * or, if a player won, the winning player's symbol ('X' or 'O').
     */
    private char getGameStatus() {
        int row;
        int col;

        Map<Character, Integer> lineCounts = new HashMap<>();

        for (row = 0; row < this.boardSize; row++) {
            countLine(getLineStatus(this.board[row]), lineCounts);
        }

        for (col = 0; col < this.boardSize; col++) {
            countLine(getLineStatus(getColumn(col)), lineCounts);
        }

        countLine(getLineStatus(getMajorDiag()), lineCounts);
        countLine(getLineStatus(getSubDiag()), lineCounts);

        boolean player1Won = lineCounts.getOrDefault(players[0].symbol, 0) > 0;
        boolean player2Won = lineCounts.getOrDefault(players[1].symbol, 0) > 0;
        boolean boardIsFull = lineCounts.getOrDefault(' ', 0) == 0;
        if (player1Won && player2Won) // both players completed lines, it is a tie
            return 'T';
        if (player1Won)
            return players[0].symbol;
        if (player2Won)
            return players[1].symbol;
        if (boardIsFull)
            return 'T';
        return '?';
    }


    /**
     * Creates a textual representation of the game board
     *
     * @return A String representing the game board in the aforementioned format.
     */
    public String toString() {
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
     * The first player to play is chosen uniformly at random.
     *
     * @return A character representing the game's result: 'H' if the human player won, 'C' if the CPU won, '
     * T' if there was a tie, or 'Q' if the human quit the game.
     */
    private char playSingleGame() {
        this.resetGame();
        System.out.println("\n---------- NEW GAME ----------\n");
        System.out.println(this);
        int turn = (int) (2 * Math.random());
        char res;
        do {
            Move move = this.players[turn].pickMove();
            if (move == null) {
                return 'Q';
            }
            this.executeMove(move, this.players[turn].getSymbol());
            System.out.println(this);
            if (turn == 0) {  // changes turns from one player to the other
                turn = 1;
            } else turn = 0;
            res = this.getGameStatus();
            switch (res) {
                case 'O':
                    res = 'C';
                    break;
                case 'X':
                    res = 'H';
            }
        } while (res == '?');
        return res;
    }

    /**
     * Runs consecutive Tic-tac-toe games until the user gets tired and quits.
     * When the user quits, the win-loss-tie statistics are printed.
     *
     * @params args The first argument represents the desired game board size, which should be an integer in [1,9].
     * If the provided board size does not comply with these rules or if no argument is provided,
     * a default game board size of 3 x 3 will be used.
     */
    public static void main(String[] args) {
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
        GameStats gStats = new GameStats() {
        };
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