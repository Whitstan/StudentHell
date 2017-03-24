package studenthell.view;

import studenthell.model.ImageLoader;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int WIDTH = 32, HEIGHT = 32;
        //private static final int BOSS_WIDTH = 63, BOSS_HEIGHT = 63;
	
	public static BufferedImage player, exam1, exam2, exam3, exam4, exam5, exam6, exam7, exam8;

	public static void init(){
            SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
            SpriteSheet enemySheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemy.png"));
            player = playerSheet.crop(0, 0, WIDTH, HEIGHT);
            exam1 = enemySheet.crop(0, 0, 50, 200);
            exam2 = enemySheet.crop(50, 0, 50, 200);
            exam3 = enemySheet.crop(100, 0, 50, 200);
            exam4 = enemySheet.crop(150, 0, 50, 200);
            exam5 = enemySheet.crop(200, 0, 50, 200);
            exam6 = enemySheet.crop(250, 0, 50, 200);
            exam7 = enemySheet.crop(300, 0, 50, 200);
            exam8 = enemySheet.crop(350, 0, 50, 200);
            //SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheetboss.png"));
            //GT
            //player = sheet.crop(1, 33, BOSS_WIDTH, BOSS_HEIGHT);
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
