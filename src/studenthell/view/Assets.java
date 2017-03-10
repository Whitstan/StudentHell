package studenthell.view;

import studenthell.model.ImageLoader;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int WIDTH = 32, HEIGHT = 32;
	
	public static BufferedImage player/*, exam, exam2*/;

	public static void init(){
            SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
            player = sheet.crop(0, 0, WIDTH, HEIGHT);
            //exam = sheet.crop(WIDTH, 0, WIDTH, HEIGHT);
            //exam2 = sheet.crop(WIDTH*2, 0, WIDTH, HEIGHT);
	}
	
}
