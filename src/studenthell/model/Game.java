package studenthell.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JTextArea;
import studenthell.Launcher;
import studenthell.controller.DataSource;
import studenthell.controller.HighScore;
import studenthell.view.Assets;
import studenthell.view.Display;
import studenthell.controller.HighScoreEntityController;
import studenthell.controller.HighScoreEntity;

public class Game implements Runnable, Behavior<Enemy> {

    private Display display;
    public int width, height;
    public String title;
    public String neptun;
    
    private int difficulty = 1;
    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private BufferedImage background;
    private Graphics g;
    private final KeyManager keyManager;

    private Player player;

    private int t = 0;
    private int enemiesPerLevel = 30;
    private long money = 10000;
    private long lastMoney = money;
    private long score = money;
    private int stage = 1;
    private boolean gameOver = false;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();
    
    private HighScoreEntityController highScoreEntityController = new HighScoreEntityController();
    
    /*
     * This method returns the long value of the current money
     */
    public long getMoney() {
        return money;
    }
    
    /**
     *@param title the title of the window
     *@param width: int value of the width of the canvas
     *@param height: int value of the height of the canvas
     *@param difficulty: Launcher.EDifficulty the difficulty of the game
     *@param neptun: String as the name of the player
     */
    
    public Game(String title, int width, int height, Launcher.EDifficulty difficulty, String neptun){
        try {
            DataSource.getInstance().getConnection().close();
        } catch (SQLException ex) {
            System.exit(1);
        }
        
        this.width = width;
        this.height = height;
        this.title = title;
        this.neptun = neptun;
        keyManager = new KeyManager();
        
        switch(difficulty){
            case DIF2:
                this.difficulty = 2;
                break;
            case DIF3:
                this.difficulty = 3;
                break;
            case DIF4:
                this.difficulty = 4;
                break;    
        }
    }

    private void init(){
        Random r = new Random();
        
        display = new Display(title, width, height);
        Assets.init();
        background = ImageLoader.loadImage("/textures/background.jpg");
        
        display.getFrame().addKeyListener(keyManager);
        player = new Player(this,368,500,39,63);
        
        //Periodic apperance of enemies
        Timer timer = new Timer();
 
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (t != enemiesPerLevel){
                    int type = r.nextInt(5-1)+1;
                    int width,height;
                    int xPosition = r.nextInt(1100);
                    switch(type){
                        case 1:
                            width = 35;
                            height = 81;
                            break;
                        case 2:
                            width = 36;
                            height = 80;
                            break;
                        case 3:
                            width = 43;
                            height = 80;
                            break;
                        case 4:
                            width = 43;
                            height = 83;
                            break;
                        case 5:
                            width = 35;
                            height = 81;
                            break;
                        default:
                            width = 81;
                            height = 78;
                            break;
                    }
                    listOfEnemies.add(new Enemy(Game.this,xPosition,-100,width,height,type));
                    t++;
                }

            }
        }, 0, 500/difficulty);
        // - Periodic apperance of enemies
    }

    /**
     *@return This method is return the list of enemies
     */
    public ArrayList<Enemy> getListOfEnemies(){
        return this.listOfEnemies;
    }
    
    /**
     * This method decreases the players money and removes a specified enemy from the list
     *@param i the index of the specified (removable) enemy in the listOfEnemies
     */
    public void decreaseMoney(int i) {
        lastMoney = money;
        money -= 3500;
        listOfEnemies.remove(i);
        g.setColor(Color.red);
    }

    /**
     * This method is check for is the an of the stage
     *@return true if the stage is ended otherwise false
     */
    public boolean isEndOfTheStage() {
        return t == enemiesPerLevel;
    }
    
    private void gameOver() throws SQLException{
        HighScoreEntity highScoreEntity = new HighScoreEntity();
        highScoreEntity.setPlayername(neptun);
        highScoreEntity.setScore(score);
        highScoreEntityController.addEntity(highScoreEntity);
        gameOver = true;
    }
    
    /**
     * This method manipulates the behavior of the enemies
     *@return an integer number of the enemies' number in the listOfEnemies
     */
    @Override
    public int doBehaviorOfEnemies() {
        for (int i=0; i<listOfEnemies.size()-1; i++){
            if (player.intersects(listOfEnemies.get(i))){
                decreaseMoney(i);
            }
            if (listOfEnemies.get(i).getY() > height){
                listOfEnemies.remove(i);
                score += 3500;
            }
        }
        return listOfEnemies.size();
    }
    
    private void tick(){
        if(this.money <= 0) {
            try {
                gameOver();
                display.destroyWindow();
                
                HighScore frame = new HighScore();
                frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
                frame.pack();
                frame.setVisible(true);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        keyManager.tick();
        player.tick();
        for (int i=0; i < listOfEnemies.size(); i++){
            listOfEnemies.get(i).tick();
        }
        
        doBehaviorOfEnemies();

        if (isEndOfTheStage()){
            stage += 1;
            enemiesPerLevel = t + 20;
            lastMoney = money;
            money += 7000;
        }
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        //Drawing
        g.drawImage(background,0,0,null);
        g.drawImage(Assets.player, (int)player.getX(), (int)player.getY(), null);
        for (int i=0; i<listOfEnemies.size(); i++){
            switch (listOfEnemies.get(i).getType()){
                case 1:
                    g.drawImage(Assets.exam1, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 2:
                    g.drawImage(Assets.exam2, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 3:
                    g.drawImage(Assets.exam3, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 4:
                    g.drawImage(Assets.exam4, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 5:
                    g.drawImage(Assets.exam5, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
            }
        }
        
        g.setFont(new Font("Arial Black", Font.PLAIN, 20));        
        if(lastMoney == money) {
           g.setColor(Color.black);
           g.drawString("Bankszámlán: " + Long.toString(money), 950, 50); 
        } else if(lastMoney < money) {
           g.setColor(Color.green);
           g.drawString("Bankszámlán: " + Long.toString(money), 950, 50); 
           
        } else {
           g.setColor(Color.red); 
           g.drawString("Bankszámlán: " + Long.toString(money), 950, 50);
        }
        
        g.drawString("Level: " + stage, 100, 50);
        //Drawing-end

        bs.show();
        g.dispose();
    }
    
    /**
     * This method returns the difficulty of the Game
     *@return the difficulty
     */
    public int getDifficulty(){
        return difficulty;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    @Override
    public void run(){
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1 && !gameOver){
                tick();
                render();
                delta--;
            }

            if(timer >= 1000000000){
                timer = 0;
            }
        }

        stop();
    }

    /**
     * This method returns the KeyManager
     *@return the keyManager
     */
    public KeyManager getKeyManager(){
	return keyManager;
    }
    
    /**
     * This method starts the game
     */
    public synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method stops the game
     */
    public synchronized void stop(){
        if(!running) return;
        running = false;
        try {
            thread.join();
            
        } catch (InterruptedException e) {
            System.err.println("Exception in stop game: " + e.getMessage());
        }
    }
}