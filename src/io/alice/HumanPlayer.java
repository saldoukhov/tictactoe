package io.alice;
import java.util.Scanner;

public class HumanPlayer extends APlayer{

    /**
     * Constructor for objects of class HumanPlayer
     * @params game the tic-tac-toe game that is to be played
     * @params the character symbol to be used to represent this player's moves
     * */
    HumanPlayer(Game game, char symbol){
        super(game, symbol);
    }

    /**
     * This method asks the user to pick a tic-tac-toe move. Moves are read from the keyboard and are specified
     * by two characters rc, where r is a letter representing the row and c is a digit representing the column.
     * For instance a1 means the 1st column of the first row and c2 means the 2nd column of the 3rd row.
     * If the user specifies: a position that is outside the bound of the game board or, a position that is
     * already occupied, an appropriate error message is shown and the user is asked for another position.
     * If the user writes quit (regardless of case), the method returns null, signifying that the program should
     * terminate.
     * @return the move the user chose or null if the user wants to quit
     * */
    public Move pickMove() {
        int boardSize = game.getBoardSize();
        Move move = new Move(boardSize + 1, boardSize + 1);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Your move (quit to exit):");
            String humanMove = scanner.next();
            humanMove = humanMove.toUpperCase();
            if (humanMove.equals("QUIT")) {
                move = null;
                break;
            }

            if (humanMove.length() != 2) {
                System.out.println("Invalid Move: please specify two characters, one for the row and one for the column");
            } else{
                int row = humanMove.charAt(0) - 65;
                int col = Character.digit(humanMove.charAt(1), 10) - 1;
                move.row = row;
                move.col = col;
                if (this.game.isValidMove(move) == 'V') {
                    break;
                }switch(this.game.isValidMove(move)) {
                        case 'R':
                            char lastChar = (char)(boardSize + 64); // the character of the last row in the board
                            System.out.println("Invalid move: You must select a row between A and " + lastChar);
                            break;
                        case 'C':
                            System.out.println("Invalid move: You must select a column between 1 and " + boardSize);
                            break;
                        case 'O':
                            System.out.println("Invalid move: position already taken");
                            break;
                    }
                }
            }
        return move;
    }
}
