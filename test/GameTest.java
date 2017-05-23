import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import studenthell.Launcher;
import studenthell.model.Enemy;
import studenthell.model.Game;



public class GameTest {
    @Before
    public void setUp() {      
    }
    
    @After
    public void tearDown() {
    }
   
    @Test
    public void moneyTest() {     
        Game game = new Game("Test", 800, 600, Launcher.EDifficulty.DIF4, "TestName");
        
        for (int i = 0; i < 5; ++i) {
            game.getListOfEnemies().add(new Enemy(game, i,2,3,4,5));
        }
        
        game.decreaseMoney(3);
        
        long result = game.getMoney();
        
        assertEquals(6500,result);
        
    }
    
    @Test
    public void moneyTestForTwoEnemyDecrase() {     
        Game game = new Game("Test", 800, 600, Launcher.EDifficulty.DIF4, "TestName");
        
        for (int i = 0; i < 5; ++i) {
            game.getListOfEnemies().add(new Enemy(game, i,2,3,4,5));
        }
        
        game.decreaseMoney(3);
        game.decreaseMoney(2);
        
        long result = game.getMoney();
        
        assertEquals(3000,result);
        
    }
}
