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

public class Game implements Runnable {

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
    
    public long getMoney() {
        return money;
    }

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
                    listOfEnemies.add(new Enemy(Game.this,300,-200,50,200,2));
                    t++;
                }

            }
        }, 0, 500);
        // - Periodic apperance of enemies
    }

    public ArrayList<Enemy> getListOfEnemies(){
        return this.listOfEnemies;
    }
    
    public void decreaseMoney(int i) {
        money -= 3500;
        listOfEnemies.remove(i);
    }

    public boolean isEndOfTheStage() {
        return t == enemiesPerLevel;
    }
    
    private void tick(){
        player.tick();
        keyManager.tick();
        for (int i=0; i<listOfEnemies.size(); i++){
            if (player.intersects(listOfEnemies.get(i))){
                decreaseMoney(i);
            }
            if (listOfEnemies.get(i).getY() > height){
                listOfEnemies.remove(i);
            }
            listOfEnemies.get(i).tick(); 
        }
        
        //FIXME: should appear a text in the canvas about the stage
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
        
        //Drawing-end

        bs.show();
        g.dispose();
    }
    
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
        }

        stop();
    }

    public KeyManager getKeyManager(){
	return keyManager;
    }
    
    public synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }
}