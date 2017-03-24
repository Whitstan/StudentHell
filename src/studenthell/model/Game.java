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
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();
    private int t = 0;

    public Game(String title, int width, int height, Launcher.EDifficulty difficulty, String name){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        
        switch(difficulty){
            case Medium:
                this.difficulty = 2;
                break;
            case Hard:
                this.difficulty = 3;
                break;
            case ExtremeSuicideHell:
                this.difficulty = 5;
                break;    
        }
    }

    private void init(){
        display = new Display(title, width, height);
        Assets.init();
        
        Random r = new Random();
        
        for (int i=0; i<10; i++){
            listOfEnemies.add(new Enemy(this,r.nextInt(600)+100 ,0,100,400,r.nextInt(8-1)+1));
        }
        
        for (int i=0; i<listOfEnemies.size(); i++){
            System.out.println("Enemy" + i + ": " + listOfEnemies.get(i).x + "," + listOfEnemies.get(i).y + " -> " + listOfEnemies.get(i).getType());
        }
        
        display.getFrame().addKeyListener(keyManager);
        player = new Player(this,368,500,32,32);
        
        //Periodic apperarnce of enemies
        Timer timer = new Timer();
 
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (t < listOfEnemies.size()){
                    listOfEnemies.get(t).setActive();
                    t++;
                }
            }
        }, 0, 2000);
        
    }

    private void tick(){
        player.tick();
        keyManager.tick();
        for (int i=0; i<listOfEnemies.size(); i++){
            listOfEnemies.get(i).tick();
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
            if (listOfEnemies.get(i).isActive()){
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
                    case 6:
                        g.drawImage(Assets.exam6, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                        break;
                    case 7:
                        g.drawImage(Assets.exam7, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                        break;
                    case 8:
                        g.drawImage(Assets.exam8, (int)listOfEnemies.get(i).getX(), (int)listOfEnemies.get(i).getY(), null);
                        break;
                }

            }
        }
        
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