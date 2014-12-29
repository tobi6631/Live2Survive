/*
 * (C) Tobias Development 2014
 */

package game.tdev.main;

import engine.silnik.WorldGenerator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author TKB
 */
public class Game {
    
    public Image worldOnePixelData;
    public boolean renderWorldOne = true;
    public WorldGenerator worldOne;
    
    //WO 2
    
    public void init(Image world1) {
        worldOnePixelData = world1;
    }
    
    public void update(GameContainer gc, int delta) {
        
    }
    
    public void render(GameContainer gc, Graphics g) {
        if(renderWorldOne) {
            worldOne = new WorldGenerator(worldOnePixelData, g);
            worldOne.run();
            renderWorldOne = false;
        }
    }
}
