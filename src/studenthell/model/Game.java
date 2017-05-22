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
     * This method returns the long value of the current money of the Player
     */
    public long getMoney() {
        return money;
    }
    
    /*
     * The constructor have to be given whit theese arguments:
     * Title : String
     * Width: int value of the width of the canvas
     * Height: int value of the height of the canvas
     * Difficulty: Launcher.EDifficulty the difficulty of the game
     * Name: String as the name of the Game
     */
    public Game(String title, int width, int height, Launcher.EDifficulty difficulty, String name){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        
        switch(difficulty){
            case Nem_az_erősségem_a_matek:
                this.difficulty = 5;
                break;
            case Talán_nem_ide_kéne_jelentkezni:
                this.difficulty = 10;
                break;
            case Ötször_öt_egyenlő_harminchat:
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
                    listOfEnemies.add(new Enemy(Game.this,300,-200,50,200,r.nextInt(8-1)+1));
                    t++;
                }

            }
        }, 0, 500);
        // - Periodic apperance of enemies
    }

    /*
     * This method is return the listOfEnemies state
     */
    public ArrayList<Enemy> getListOfEnemies(){
        return this.listOfEnemies;
    }
    
    /*
     * This method is decrease the players money and
     * remove the enemy what caused the collosion
     */
    public void decreaseMoney(int i) {
        money -= 3500;
        listOfEnemies.remove(i);
    }

    /*
     * This method is check for is the and of the stage or not
     * return true if the stage is ended or false if not
     */
    public boolean isEndOfTheStage() {
        return t == enemiesPerLevel;
    }
    
    
    /*
     * This method do the behavior of the enemies
     * decrase money when collide occurs and
     * remove that concrete enemy from the listOfEnemies list
     * return with the integer number of the enemies int the listOfEnemies
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
            System.out.println("New stage " + stage);
            enemiesPerLevel = t + 10;
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
    
    /*
     * This method returns the difficulty of the Game
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

    /*
     * This method return the KeyManager
     */
    public KeyManager getKeyManager(){
	return keyManager;
    }
    
    /*
     * This method start the Game
     */
    public synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /*
     * This method stop the Game
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