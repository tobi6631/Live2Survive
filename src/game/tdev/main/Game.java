/*
 * (C) Tobias Development 2014
 */

package game.tdev.main;

import engine.silnik.tiles.StaticTile;
import engine.silnik.tiles.util.TileType;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author TKB
 */
public class Game {
    
    public Image worldPixelData[] = new Image[2];
    public boolean renderWorldOne = true;
    public int worldGenY = 0;
    public ArrayList<StaticTile> staticTiles = new ArrayList<>();
    public SpriteSheet worldSprite;
    
    public void init(Image[] world, SpriteSheet worldSprite) {
        worldPixelData = world;
        this.worldSprite = worldSprite;
    }
    
    public void update(GameContainer gc, int delta) {
        if(renderWorldOne) {
            runWorldGenerator(worldPixelData[0]);
            renderWorldOne = false;
        }
    }
    
    public void render(GameContainer gc, Graphics g) {
        for(int i = 0; i < staticTiles.size(); i++) {
            staticTiles.get(i).render();
        }
    }
    
    public void runWorldGenerator(Image world) {
        Image pixelData = world;
        int worldGenWidth = world.getWidth();
        int worldGenHeight = world.getHeight();
        
        for (int worldGenx = 0; worldGenx < worldGenWidth; worldGenx++) {
           
            if (worldGenx == worldGenWidth - 1) {
                worldGenY += 1;
                worldGenx = 0;
                if (worldGenY == worldGenHeight) {
                    break;
                }
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(255, 0, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_VENSTREHJØRNE_TOP_0_0, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(127, 0, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_VENSTRESIDE_0_1, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(255, 106, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_VENSTREHJØRNE_DOWN_0_2, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(127, 51, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_TOPDOWN_1_0, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(255, 216, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.GRÆS_1_1, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(127, 106, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_DOWNTOP_1_2, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(182, 255, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_HØJREHJØRNE_TOP_2_0, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(91, 127, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_HØJRESIDE_2_1, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(76, 255, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD_TIL_GRÆS_HØJREHJØRNE_DOWN_2_2, worldSprite);
            }
        }
    }
    
    public void addStaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        staticTiles.add(new StaticTile(x, y, type, spriteSheet));
    }
}
