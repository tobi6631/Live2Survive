/*
 * (C) Tobias Development 2014
 */
package game.tdev.main;

import engine.silnik.Option;
import engine.silnik.tiles.StaticTile;
import engine.silnik.tiles.TreeTile;
import engine.silnik.tiles.util.TileType;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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

    public void init(Image[] world, SpriteSheet worldSprite) {
        worldPixelData = world;
        this.worldSprite = worldSprite;
        worldPerformShape = new Rectangle(1, 1, Option.getWidth() - 1, Option.getHeight() - 2);

        playerX = Option.getWidth() / 2 - 32;
        playerY = Option.getHeight() / 2 - 32;
    }

    public void update(GameContainer gc, int delta) {

        //render worlds
        if (renderWorldOne) {
            runWorldGenerator(worldPixelData[0]);
            renderWorldOne = false;
        }

        //update Tiles
        staticTiles.stream().forEach((staticTile) -> {
            if (worldPerformShape.intersects(staticTile.spriteShape)) {
                staticTile.insidePerformFrame = true;
            } else if (!worldPerformShape.contains(staticTile.spriteShape)) {
                staticTile.insidePerformFrame = false;
            }
        });

        treeTiles.stream().forEach((treeTile) -> {
            treeTile.update();
            if (worldPerformShape.intersects(treeTile.spriteShape)) {
                treeTile.insidePerformFrame = true;
            } else if (!worldPerformShape.contains(treeTile.spriteShape)) {
                treeTile.insidePerformFrame = false;
            }
        });

        //Player
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            playerY += 0.2F * delta;
        }

        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            playerX += 0.2F * delta;
        }

        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            playerY -= 0.2F * delta;
        }

        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            playerX -= 0.2F * delta;
        }
    }

    public void render(GameContainer gc, Graphics g, Image[] player) {
        staticTiles.stream().forEach((staticTile) -> {
            staticTile.render(playerX, playerY, g);
        });
        
        player[0].draw(gc.getWidth() / 2 - 16, gc.getHeight() / 2 - 32);

        treeTiles.stream().forEach((treeTile) -> {
            treeTile.render(playerX, playerY, g);
        });

        if (Option.getDebugMode()) {
            g.setLineWidth(2);
            g.setColor(Color.red);
            g.draw(worldPerformShape);
        }

        //Player
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
        }
    }

    public void addStaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        staticTiles.add(new StaticTile(x, y, type, spriteSheet));
    }

    public void addTreeTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        treeTiles.add(new TreeTile(x, y, type, spriteSheet));
    }
}
