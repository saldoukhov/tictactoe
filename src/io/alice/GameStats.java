package io.alice;

public class GameStats {
    /**
     * represents the number of games won by the human player, so far
     */
    private int nWins = 0;
    /**
     * represents the number of games tied, so far
     */
    private int nTies = 0;
    /**
     * represents the number of games lost by the human player, so far
     */
    private int nLosses = 0;

    /**
     * An empty constructor for objects of class GameStats.
     */
    GameStats() {
    }

    /**
     * Increments the number of human wins.
     */
    void recordWin() {
        nWins = nWins + 1;
    }

    /**
     * Increments the number of ties.
     */
    void recordTie() {
        nTies = nTies + 1;
    }

    /**
     * Increments the number of human losses.
     */
    void recordLoss() {
        nLosses = nLosses + 1;
    }

    /**
     * Returns a textual representation of the statistics contained in this object.
     *
     * @return a textual representation of the statistics contained in this object
     */
    public String toString() {
        int nTotal = this.nWins + this.nTies + this.nLosses;
        return nTotal + " games: " + this.nWins + " wins, " + this.nTies + " ties, " + nLosses + " losses.";
    }
}
