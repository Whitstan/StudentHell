package studenthell.model;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import studenthell.Launcher;
import studenthell.view.Assets;
import studenthell.view.Display;

public class Game implements Runnable, Behavior<Enemy> {

    private Display display;
    public int width, height;
    public String title;
    
    private int difficulty = 1;
    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;
    private final KeyManager keyManager;

    private Player player;

    private int t = 0;
    private int enemiesPerLevel = 30;
    private long money = 10000;
    private int stage = 1;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();
    
    /*
     * This method returns the long value of the current money
     */
    public long getMoney() {
        return money;
    }
    
    /**
     * The constructor have to be given with theese arguments:
     *@param title the title of the window
     *@param width: int value of the width of the canvas
     *@param height: int value of the height of the canvas
     *@param difficulty: Launcher.EDifficulty the difficulty of the game
     *@param name: String as the name of the Game
     */
    
    public Game(String title, int width, int height, Launcher.EDifficulty difficulty, String name){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        
        switch(difficulty){
            case DIF2:
                this.difficulty = 5;
                break;
            case DIF3:
                this.difficulty = 10;
                break;
            case DIF4:
                this.difficulty = 15;
                break;    
        }
    }

    private void init(){
        Random r = new Random();
        
        display = new Display(title, width, height);
        Assets.init();
        
        display.getFrame().addKeyListener(keyManager);
        player = new Player(this,368,500,32,32);
        
        //Periodic apperance of enemies
        Timer timer = new Timer();
 
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (t != enemiesPerLevel){
                    int type = r.nextInt(8-1)+1;
                    int width = 52;
                    int height = 88;
                    int xPosition = r.nextInt(700);
                    if (type == 5){
                        width = 90;
                    }
                    listOfEnemies.add(new Enemy(Game.this,xPosition,-200,width,height,type));
                    t++;
                }

            }
        }, 0, 500);
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
        money -= 3500;
        listOfEnemies.remove(i);
    }

    /**
     * This method is check for is the an of the stage
     *@return true if the stage is ended otherwise false
     */
    public boolean isEndOfTheStage() {
        return t == enemiesPerLevel;
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
            }
            listOfEnemies.get(i).tick(); 
        }
        return listOfEnemies.size();
    }
    
    private void tick(){
        player.tick();
        keyManager.tick();
        
        doBehaviorOfEnemies();

        if (isEndOfTheStage()){
            stage += 1;
            enemiesPerLevel = t + 20;
            money += 10000;
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
        g.drawImage(Assets.player, (int)player.getX(), (int)player.getY(), null);
        for (int i=0; i<listOfEnemies.size(); i++){
            switch (listOfEnemies.get(i).getType()){
                case 1:
                    //g.drawRect((int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), 50, 200);
                    g.drawImage(Assets.exam1, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 2:
                    //g.drawRect((int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), 50, 200);
                    g.drawImage(Assets.exam2, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 3:
                    //g.drawRect((int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), 50, 200);
                    g.drawImage(Assets.exam3, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 4:
                    //g.drawRect((int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), 50, 200);
                    g.drawImage(Assets.exam4, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
                case 5:
                    //g.drawRect((int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), 50, 200);
                    g.drawImage(Assets.exam5, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                    break;
            }
        }
        
        g.drawString("Keret: " + Long.toString(money), 600, 50);
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

            if(delta >= 1){
                tick();
                render();
                delta--;
            }

            if(timer >= 1000000000){
                timer = 0;
            }
            if(this.money == 0 || this.money < 0) {
                //End the game
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