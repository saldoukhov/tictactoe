package io.alice;

public class GameStats {
    /** represents the number of games won by the human player, so far */
    int nwins = 0;
    /** represents the number of games tied, so far */
    int nties = 0;
    /** represents the number of games lost by the human player, so far */
    int nlosses = 0;

    /**
     * An empty constructor for objects of class GameStats.
     * */
    public GameStats(){}

    /** Increments the number of human wins. */
    public void recordWin(){
        nwins = nwins + 1;
    }
    /** Increments the number of ties. */
    public void recordTie(){
        nties = nties + 1;
    }
    /** Increments the number of human losses. */
    public void recordLoss(){
        nlosses = nlosses + 1;
    }
    /**
     * Returns a textual representation of the statistics contained in this object.
     * @return a textual representation of the statistics contained in this object
     * */
    public String toString(){
        int ntotal = this.nwins + this.nties + this.nlosses;
        return  ntotal + " games: " + this.nwins + " wins, " + this.nties + " ties, " + nlosses + " losses.";
    }
}
