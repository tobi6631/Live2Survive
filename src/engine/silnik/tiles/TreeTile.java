package engine.silnik.tiles;

import engine.silnik.Option;
import engine.silnik.Sound;
import engine.silnik.Vector2i;
import engine.silnik.item.Item;
import engine.silnik.tiles.util.TileType;
import engine.silnik.tiles.util.TileTypeSpriteASGN;
import engine.silnik.main.Game;
import engine.silnik.main.Main;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class TreeTile {

    private final int x;
    private final int y;
    private int _x;
    private int _y;
    private Image sprite;
    public Shape spriteShape;
    public Shape playerFrontBack;
    public Shape playerCollision;
    public boolean insidePerformFrame = true;
    public int health = 20;
    private boolean active = true;
    private final Random soundNumberGenerator;
    private int soundNumber;

    public TreeTile(int x, int y, TileType type, SpriteSheet spriteSheet) {
        this.x = x;
        this.y = y;

        sprite = TileTypeSpriteASGN.asignSprite(spriteSheet, type);
        spriteShape = new Rectangle(x, y, 160, 160);
        playerFrontBack = new Rectangle(x, y, 160, 160);
        playerCollision = new Rectangle(x, y, 30, 10);
        
        soundNumberGenerator = new Random();
    }

    public void render(float xP, float yP, Graphics g) {
        if (insidePerformFrame) {
            sprite.draw(x * Option.getBitRate() + xP, y * Option.getBitRate() + yP);

            _x = x * Option.getBitRate() + Math.round(xP);
            _y = y * Option.getBitRate() + Math.round(yP);

            playerFrontBack.setCenterX(_x + 80);
            playerFrontBack.setCenterY(_y + 45);
            playerCollision.setCenterX(_x + 80);
            playerCollision.setCenterY(_y + 123);

            //Destroying
            if (Main.mouseOver(new Vector2i(_x, _y), sprite, Main.inputAll) && health > 0) {
                g.setColor(Color.orange);
                g.drawString("* Left click to fell tree" + "\n  "
                        + health + "/20", Main.inputAll.getMouseX() + 10, Main.inputAll.getMouseY());
                if (Main.inputAll.isMousePressed(0)) {
                    health -= 2;
                    g.drawString("-2", Main.inputAll.getMouseX(), Main.inputAll.getMouseY());
                    
                    soundNumber = soundNumberGenerator.nextInt(3);
                    
                    if(soundNumber == 0) {Main.mixer.playSound(Sound.WOOD_HIT1);}
                    else if(soundNumber == 1) {Main.mixer.playSound(Sound.WOOD_HIT2);}
                    else if(soundNumber == 2) {Main.mixer.playSound(Sound.WOOD_HIT3);}
                    else if(soundNumber == 3) {Main.mixer.playSound(Sound.WOOD_HIT4);}
                }
            }

            if (active) {
                if (health <= 0) {
                    sprite = Main.tile[4];
                    for (int i = 0; i < Game.itemSlot_bar; i++) {
                        if (Game.itemSlotBar[i].isFree()) {
                            Game.itemSlotBar[i].addItem(Item.TRÆ001, 3);
                        }else if(Game.itemSlotBar[i].contains(Item.TRÆ001)){
                            Game.itemSlotBar[i].addItem(Item.TRÆ001, 3);
                        }
                        active = false;
                        break;
                    }
                }
            }

            if (Option.getDebugMode()) {
                g.setColor(Color.red);
                g.draw(playerFrontBack);
                g.draw(playerCollision);
            }
        }

        spriteShape.setX(x * Option.getBitRate() + xP);
        spriteShape.setY(y * Option.getBitRate() + yP);
    }

    public void update() {
        if (insidePerformFrame) {
            if (playerFrontBack.intersects(Game.playerShape)) {
                Game.inFront = false;
            }

            if (playerCollision.intersects(Game.playerShape) && Game.movement == 1 && Game.inFront) {
                Game.moveable[0] = false;
            }

            if (playerCollision.intersects(Game.playerShape) && Game.movement == 2 && !Game.inFront) {
                Game.moveable[1] = false;
            }

            if (playerCollision.intersects(Game.playerShape) && Game.movement == 3 && !Game.inFront) {
                Game.moveable[2] = false;
            }

            if (playerCollision.intersects(Game.playerShape) && Game.movement == 4 && !Game.inFront) {
                Game.moveable[3] = false;
            }
        }
    }
}
