package studenthell.controller;

public class HighScoreEntity{

    public final static String[] FIELDNAMES = new String[]{"Playername", "Score"};
    private int ID;
    private String playername;
    private long score;
    
    public HighScoreEntity() {}

    public HighScoreEntity(int ID) {this.ID = ID;}
    
    public void setID(int ID){this.ID = ID;}
    
    public int getID(){return this.ID;}
    
    public String getPlayername() {return playername;}

    public long getScore() {return score;}

    public void setPlayername(String playername) {this.playername = playername;}

    public void setScore(long score) {this.score = score;}
}
