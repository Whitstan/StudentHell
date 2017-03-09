package studenthell.model;

import studenthell.model.Player;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import studenthell.view.Assets;
import studenthell.view.Display;

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;
    private final KeyManager keyManager;

    private Player player;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title, width, height);
        Assets.init();
        display.getFrame().addKeyListener(keyManager);
        player = new Player(this,350,0,32,32);
    }

    private void tick(){
        player.tick();
        keyManager.tick();
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
        
        
        //Drawing-end

        bs.show();
        g.dispose();
    }

    @Override
    public void run(){
        init();
        while(running){
            tick();
            render();
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