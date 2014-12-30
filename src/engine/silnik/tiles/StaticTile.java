package engine.silnik.tiles;

import engine.silnik.tiles.util.TileType;
import engine.silnik.tiles.util.TileTypeSpriteASGN;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


public class StaticTile {
    
    private final float x;
    private final float y;
    private final Image sprite;
    
    public StaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        this.x = x;
        this.y = y;
        
        sprite = TileTypeSpriteASGN.asignSprite(spriteSheet, type);
    }
    
    public void render() {
        sprite.draw(x * 64, y * 64);
    }
}
