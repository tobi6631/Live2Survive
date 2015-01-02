package engine.silnik.tiles;

import engine.silnik.Option;
import engine.silnik.tiles.util.TileType;
import engine.silnik.tiles.util.TileTypeSpriteASGN;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class TreeTile {
    private final float x;
    private final float y;
    private final Image sprite;
    public Shape spriteShape;
    public boolean insidePerformFrame = true;

    public TreeTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        this.x = x;
        this.y = y;

        sprite = TileTypeSpriteASGN.asignSprite(spriteSheet, type);
        spriteShape = new Rectangle(x, y, 160, 160);
    }

    public void render(float xP, float yP, Graphics g) {
        if (insidePerformFrame) {
            sprite.draw(x * Option.getBitRate() + xP, y * Option.getBitRate() + yP);
        }
        spriteShape.setX(x * Option.getBitRate() + xP);
        spriteShape.setY(y * Option.getBitRate() + yP);
    }
    
    public void update() {
        
    }
}
