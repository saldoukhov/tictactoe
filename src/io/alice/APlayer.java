package io.alice;

public class APlayer {
    /** character to represent the player's moves on the board */
    protected char symbol;
    /** the game the player is playing */
    protected Game game;

    /** Empty constructor for objects of class APlayer.*/
    protected APlayer(){}

    /**
     * A constructor for objects of class APlayer.
     * @params game the tic-tac-toe game that is to be played
     * @params symbol the character symbol to be used to represent this player's moves
     * */
    protected APlayer(Game game, char symbol){
        this.game = game;
        this.symbol = symbol;
    }

    /**
     * Returns the symbol that represents this player.
     * @return a char representing the player's symbol*/
    public char getSymbol(){
        return this.symbol;
    }

    /** Makes the player pick his next move. Each concrete type of player should implement this method according
     * to its own semantics. The only thing that is required is that the method returns a valid move
     * (i.e. an unocuppied position within the bounds of the game board)
     * or null to signify that the player desires to exit the program.
     * @return the move picked by the player*/
    public Move pickMove(){
        return null; // null is the default return
    }
}
