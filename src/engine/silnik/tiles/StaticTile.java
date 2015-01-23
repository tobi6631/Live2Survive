package engine.silnik.tiles;

import engine.silnik.Option;
import engine.silnik.tiles.util.TileType;
import engine.silnik.tiles.util.TileTypeSpriteASGN;
import game.tdev.main.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class StaticTile {

    private final float x;
    private final float y;
    private final Image sprite;
    public Shape spriteShape;
    public boolean insidePerformFrame = true;

    public StaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        this.x = x;
        this.y = y;

        sprite = TileTypeSpriteASGN.asignSprite(spriteSheet, type);
        spriteShape = new Rectangle(x, y, Option.getBitRate(), Option.getBitRate());
    }

    public void render(float xP, float yP, Graphics g) {
        if (insidePerformFrame) {
            sprite.draw(x * Option.getBitRate() + xP, y * Option.getBitRate() + yP, Option.getBitRate(), Option.getBitRate());
            
            if(Option.getDebugMode()) {
                g.setColor(Color.yellow);
                g.draw(spriteShape);
            }
        }
        
        spriteShape.setX(x * Option.getBitRate() + xP);
        spriteShape.setY(y * Option.getBitRate() + yP);
    }
}
