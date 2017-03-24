package studenthell.model;

import java.awt.geom.Rectangle2D;

public class Enemy extends Rectangle2D.Double{

    private final Game game;
    private boolean outOfRoom = false;
    private boolean active = false;
    private int type = 0;
    
    public Enemy(Game game, double x, double y, double w, double h, int type) {
        super(x, y, w, h);
        this.game = game;
        this.type = type;
    }
    
    public void tick(){
        if (active){
            if (y <= game.height){
                y += game.getDifficulty();   
            }
            else{
                outOfRoom = true;
            }
        }
    }
    
    public boolean getOutOfRoom(){
        return outOfRoom;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setActive(){
        active = true;
    }
}

