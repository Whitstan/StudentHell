import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import studenthell.Launcher;
import studenthell.model.Enemy;
import studenthell.model.Game;
import studenthell.model.Player;



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
    public void endOfTheStageTest() {     
        Game game = new Game("Test", 800, 600, Launcher.EDifficulty.DIF4, "TestName");
        
        for (int i = 0; i < 5; ++i) {
            game.getListOfEnemies().add(new Enemy(game, i,2,3,4,5));
        }
        
        boolean result = game.isEndOfTheStage(); 
        assertEquals(false,result);
    }
    
    @Test
    public void doBehaviorOfEnemiesTest() {     
        Game game = new Game("Test", 800, 600, Launcher.EDifficulty.DIF4, "TestName");
        Player player = new Player(game,368,500,39,63);
        game.setPlayer(player);
        
        game.getListOfEnemies().add(new Enemy(game, 370,2000,20,30,5));
        
        for (int i = 0; i < 5; ++i) {
            game.getListOfEnemies().add(new Enemy(game, i,2,3,4,5));
        }
        int result = game.doBehaviorOfEnemies();
        assertEquals(5,result);
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
