/*
 * (C) Tobias Development 2014
 */
package game.tdev.main;

import engine.silnik.ContentLoader;
import engine.silnik.Option;
import engine.silnik.tiles.StaticTile;
import engine.silnik.tiles.TreeTile;
import engine.silnik.tiles.util.TileType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author TKB
 */
public class Game {

    public Image worldPixelData[] = new Image[2];
    public boolean renderWorldOne = true;
    public int worldGenY = 0;
    public ArrayList<StaticTile> staticTiles = new ArrayList<>();
    public ArrayList<TreeTile> treeTiles = new ArrayList<>();
    public SpriteSheet worldSprite;
    public static Shape worldPerformShape;

    //Player
    public float playerX;
    public float playerY;
    public Animation player[] = new Animation[5];
    public Animation cur_Player;
    public Image playerIdle;
    public static int movement;
    public static boolean inFront = true;
    public static Shape playerShape;
    public static boolean moveable[] = new boolean[4];

    public void init(Image[] world, SpriteSheet worldSprite) {
        worldPixelData = world;
        this.worldSprite = worldSprite;
        worldPerformShape = new Rectangle(1, 1, Option.getWidth() - 1, Option.getHeight() - 2);

        try {
            player[0] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerLeft.png"), 32, 48), 100);
            player[1] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerRight.png"), 32, 48), 100);
            player[2] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerFront.png"), 32, 50), 100);
            player[3] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerBack.png"), 32, 50), 100);
            player[4] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerIdle.png"), 32, 50), 100);
            
            moveable[0] = true;
            moveable[1] = true;
            moveable[2] = true;
            moveable[3] = true;
        } catch (SlickException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        playerX = Option.getWidth() / 2 - 32;
        playerY = Option.getHeight() / 2 - 32;
        
        playerShape = new Rectangle(playerX + 16, playerY, 32, 50);
    }

    public void updatePlayer(GameContainer gc, int delta) {
        
        //Player
        if (gc.getInput().isKeyDown(Input.KEY_W) && moveable[0]) {
            playerY += 0.12F * delta;
            movement = 1;
            moveable[1] = true;
            moveable[2] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_A) && moveable[1]) {
            playerX += 0.12F * delta;
            movement = 2;
            moveable[0] = true;
            moveable[2] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_S) && moveable[2]) {
            playerY -= 0.12F * delta;
            movement = 3;
            moveable[0] = true;
            moveable[1] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_D) && moveable[3]) {
            playerX -= 0.12F * delta;
            movement = 4;
            moveable[0] = true;
            moveable[2] = true;
            moveable[1] = true;
        } else {
            movement = 0;
        }
    }

    public void render(GameContainer gc, Graphics g) {

        //render worlds
        if (renderWorldOne) {
            runWorldGenerator(worldPixelData[0]);
            renderWorldOne = false;
        }

        staticTiles.stream().forEach((staticTile) -> {
            staticTile.render(playerX, playerY, g);
        });

        if (!inFront) {
            //Player
            if (movement == 0) {
                player[4].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 1) {
                player[3].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 2) {
                player[0].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 3) {
                player[2].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 4) {
                player[1].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            }
        }

        treeTiles.stream().forEach((treeTile) -> {
            treeTile.render(playerX, playerY, g);
        });
        
        if (inFront) {
            //Player
            if (movement == 0) {
                player[4].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 1) {
                player[3].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 2) {
                player[0].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 3) {
                player[2].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 4) {
                player[1].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            }
        }
        
        inFront = true;

        if (Option.getDebugMode()) {
            g.setColor(Color.red);
            g.draw(worldPerformShape);
            g.drawString("M" + movement, playerX, playerY);
            g.draw(playerShape);
        }
    }

    public void runWorldGenerator(Image world) {
        Image pixelData = world;
        int worldGenWidth = world.getWidth();
        int worldGenHeight = world.getHeight();
        int tiles = 0;//for debuging;

        for (int worldGenx = 0; worldGenx < worldGenWidth; worldGenx++) {

            if (worldGenx == worldGenWidth - 1) {
                worldGenY += 1;
                worldGenx = 0;
                if (worldGenY == worldGenHeight) {
                    System.out.println("World generated successfully!, TILES: " + tiles);
                    renderWorldOne = false;
                    break;
                }
            }

            tiles += 1;

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 255, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.GRÆS000, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(127, 51, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD001, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(64, 64, 64))) {
                addStaticTile(worldGenx, worldGenY, TileType.STEN002, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 127, 14))) {
                addStaticTile(worldGenx, worldGenY, TileType.GRÆS000, worldSprite);
                addTreeTile(worldGenx, worldGenY, TileType.TRÆ003, worldSprite);
            }
            
            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 0, 255))) {
                addStaticTile(worldGenx, worldGenY, TileType.VAND005, worldSprite);
            }
        }
    }

    public void addStaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        staticTiles.add(new StaticTile(x, y, type, spriteSheet));
    }

    public void addTreeTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        treeTiles.add(new TreeTile(x, y, type, spriteSheet));
    }
}
