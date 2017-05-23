package studenthell.view;

import studenthell.model.ImageLoader;
import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, exam1, exam2, exam3, exam4, exam5, background;

	public static void init(){
            SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
            SpriteSheet enemySheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemy2.png"));
            player = playerSheet.crop(1, 33, 39, 63);
            exam1 = enemySheet.crop(10, 2, 35, 88);
            exam2 = enemySheet.crop(60, 4, 36, 80);
            exam3 = enemySheet.crop(108, 4, 43, 80);
            exam4 = enemySheet.crop(8, 93, 43, 83);
            exam5 = enemySheet.crop(63, 98, 81, 78);
	}
	
}
