package studenthell.model;

import java.awt.geom.Rectangle2D;

public class Player extends Rectangle2D.Double{

    private final Game game;
    
    public Player(Game game, double x, double y, double w, double h) {
        super(x, y, w, h);
        this.game = game;
    }
    
    public void tick() {
        if (game.getKeyManager().up){setY(this.y - 5.0);}
        if (game.getKeyManager().down){setY(this.y + 5.0);}
        if (game.getKeyManager().left){setX(this.x - 5.0);}
        if (game.getKeyManager().right){setX(this.x + 5.0);}
    }
    
    public void setX(double x){this.x = x;}
    
    public void setY(double y){this.y = y;}
}
