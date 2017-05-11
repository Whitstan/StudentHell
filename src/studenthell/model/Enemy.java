package studenthell.model;

import java.awt.geom.Rectangle2D;

public class Enemy extends Rectangle2D.Double{

    private final Game game;
    private int type = 0;
    
    public Enemy(Game game, double x, double y, double w, double h, int type) {
        super(x, y, w, h);
        this.game = game;
        this.type = type;
    }
    
    public void tick(){
        y += game.getDifficulty();   
    }
    
    public int getType(){
        return this.type;
    }
}