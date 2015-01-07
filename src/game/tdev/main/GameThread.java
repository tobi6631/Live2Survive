/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tdev.main;

import engine.silnik.tiles.StaticTile;
import engine.silnik.tiles.TreeTile;
import static game.tdev.main.Game.worldPerformShape;
import java.util.ArrayList;

/**
 *
 * @author TKB
 */
public class GameThread extends Thread {

    private final ArrayList<StaticTile> staticTiles;
    private final ArrayList<TreeTile> treeTiles;

    public GameThread(ArrayList<TreeTile> treeTiles, ArrayList<StaticTile> staticTiles) {
        this.treeTiles = treeTiles;
        this.staticTiles = staticTiles;
        this.setName("GameThread");
    }

    @Override
    public void run() {
        updateTiles();
        System.out.println("init1");
    }

    public void updateTiles() {
        //update Tiles
        while (this.isAlive()) {
            for (StaticTile staticTile : staticTiles) {
                if (worldPerformShape.intersects(staticTile.spriteShape)) {
                    staticTile.insidePerformFrame = true;
                } else if (!worldPerformShape.contains(staticTile.spriteShape)) {
                    staticTile.insidePerformFrame = false;
                }
            }

            for (TreeTile treeTile : treeTiles) {
                treeTile.update();
                if (worldPerformShape.intersects(treeTile.spriteShape)) {
                    treeTile.insidePerformFrame = true;
                } else if (!worldPerformShape.contains(treeTile.spriteShape)) {
                    treeTile.insidePerformFrame = false;
                }
            }
        }
    }
}
