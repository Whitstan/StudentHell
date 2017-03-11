package studenthell.view;

import studenthell.model.ImageLoader;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int WIDTH = 32, HEIGHT = 32;
        private static final int BOSS_WIDTH = 63, BOSS_HEIGHT = 63;
	
	public static BufferedImage player/*, exam, exam2*/;

	public static void init(){
/*
            SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
            player = sheet.crop(0, 0, WIDTH, HEIGHT);
            //exam = sheet.crop(WIDTH, 0, WIDTH, HEIGHT);
            //exam2 = sheet.crop(WIDTH*2, 0, WIDTH, HEIGHT);
*/
            
            SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheetboss.png"));
            //GT
            player = sheet.crop(1, 33, BOSS_WIDTH, BOSS_HEIGHT);
            //Simon
            //player = sheet.crop(1+64, 33, BOSS_WIDTH, BOSS_HEIGHT);
            //Gergó
            //player = sheet.crop(1, 33+64, BOSS_WIDTH, BOSS_HEIGHT);
            //HZ
            //player = sheet.crop(1+64, 33+64, BOSS_WIDTH, BOSS_HEIGHT);
            //Ásványi
            //player = sheet.crop(1, 33+128, BOSS_WIDTH, BOSS_HEIGHT);
            //Illés
            //player = sheet.crop(1+64, 33+128, BOSS_WIDTH, BOSS_HEIGHT);
            //Pataki
            //player = sheet.crop(1, 33+192, BOSS_WIDTH, BOSS_HEIGHT);
            //NagyG
            //player = sheet.crop(1+64, 33+192, BOSS_WIDTH, BOSS_HEIGHT);
	}
	
}
