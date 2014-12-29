/*
 * (C) Tobias Development 2014
 */

package engine.silnik;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class WorldGenerator {

    private final Image pixelData;
    private final int width;
    private final int height;
    private int y = 0;
    
    public WorldGenerator(Image world, Graphics g) {
        pixelData = world;
        width = world.getWidth();
        height = world.getHeight();
    }

    public void run() {
        for (int x = 0; x < width; x++) {
           
            if (x == width - 1) {
                y += 1;
                x = 0;
                if (y == height) {
                    break;
                }
            }
            
            if (pixelData.getColor(x, y).equals(new Color(255, 0, 0))) {
                pixelData.draw(x * 16, y * 16, 16, 16);
            }
        }
    }
}
