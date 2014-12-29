/*
 * (C) Tobias Development 2014
 */

package game.tdev.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author TKB
 */
public class Game {
    
    public Image worldPixelData[] = new Image[2];
    
    public boolean renderWorldOne = true;
   
    public int worldGenY = 0;
    
    public void init() {
        
    }
    
    public void update(GameContainer gc, int delta) {
        if(renderWorldOne) {
            renderWorldOne = false;
        }
    }
    
    public void render(GameContainer gc, Graphics g) {
        
    }
    
    public void runWorldGenerator(Image world) {
        Image pixelData = world;
        int worldGenWidth = world.getWidth();
        int worldGenHeight = world.getHeight();
        
        for (int x = 0; x < worldGenWidth; x++) {
           
            if (x == worldGenWidth - 1) {
                worldGenY += 1;
                x = 0;
                if (worldGenY == worldGenHeight) {
                    break;
                }
            }
            
            if (pixelData.getColor(x, worldGenY).equals(new Color(255, 0, 0))) {
                pixelData.draw(x * 16, worldGenY * 16, 16, 16);
            }
        }
    }
}
