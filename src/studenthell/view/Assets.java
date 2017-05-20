package studenthell.view;

import studenthell.model.ImageLoader;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int WIDTH = 32, HEIGHT = 32;
        //private static final int BOSS_WIDTH = 63, BOSS_HEIGHT = 63;
	
	public static BufferedImage player, exam1, exam2, exam3, exam4, exam5;

	public static void init(){
            SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
            SpriteSheet enemySheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemy2.png"));
            player = playerSheet.crop(1, 33, 40, 63);
            exam1 = enemySheet.crop(0, 0, 52, 90);
            exam2 = enemySheet.crop(52, 0, 52, 90);
            exam3 = enemySheet.crop(104, 0, 52, 90);
            exam4 = enemySheet.crop(0, 90, 52, 88);
            exam5 = enemySheet.crop(52, 90, 104, 88);
	}
	
}
