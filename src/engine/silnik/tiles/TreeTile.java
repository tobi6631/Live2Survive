package engine.silnik.tiles;

import engine.silnik.Option;
import engine.silnik.tiles.util.TileType;
import engine.silnik.tiles.util.TileTypeSpriteASGN;
import game.tdev.main.Game;
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
    public Shape playerFrontBack;
    public Shape playerCollision;
    public boolean insidePerformFrame = true;

    public TreeTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        this.x = x;
        this.y = y;

        sprite = TileTypeSpriteASGN.asignSprite(spriteSheet, type);
        spriteShape = new Rectangle(x, y, 160, 160);
        playerFrontBack = new Rectangle(x, y, 160, 160);
        playerCollision = new Rectangle(x, y, 30, 10);
    }

    public void render(float xP, float yP, Graphics g) {
        if (insidePerformFrame) {
            sprite.draw(x * Option.getBitRate() + xP, y * Option.getBitRate() + yP);
            
            playerFrontBack.setCenterX(x * Option.getBitRate() + xP + 80);
            playerFrontBack.setCenterY(y * Option.getBitRate() + yP + 45);
            playerCollision.setCenterX(x * Option.getBitRate() + xP + 80);
            playerCollision.setCenterY(y * Option.getBitRate() + yP + 123);
            
            if(Option.getDebugMode()) {
                g.draw(playerFrontBack);
                g.draw(playerCollision);
            }
        }
        
        spriteShape.setX(x * Option.getBitRate() + xP);
        spriteShape.setY(y * Option.getBitRate() + yP);
    }

    public void update() {
        if (playerFrontBack.intersects(Game.playerShape)) {
            Game.inFront = false;
        }
        
        if(playerCollision.intersects(Game.playerShape) && Game.movement == 1 && Game.inFront) {
            Game.moveable[0] = false;
        }
        
        if(playerCollision.intersects(Game.playerShape) && Game.movement == 2 && !Game.inFront) {
            Game.moveable[1] = false;
        }
        
        if(playerCollision.intersects(Game.playerShape) && Game.movement == 3 && !Game.inFront) {
            Game.moveable[2] = false;
        }
        
        if(playerCollision.intersects(Game.playerShape) && Game.movement == 4 && !Game.inFront) {
            Game.moveable[3] = false;
        }
    }
}
