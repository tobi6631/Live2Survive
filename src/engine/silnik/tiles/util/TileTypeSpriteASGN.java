/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.silnik.tiles.util;

import game.tdev.Main.Main;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author TKB
 */
public class TileTypeSpriteASGN {
    public static Image asignSprite(SpriteSheet spriteSheet, TileType type) {
        Image sprite = null;
        
        if (type == TileType.GRÆS000)  {sprite = Main.tile[0];}
        if (type == TileType.JORD001)  {sprite = Main.tile[1];}
        if (type == TileType.STEN002)  {sprite = Main.tile[2];}
        if (type == TileType.TRÆ003)  {sprite = Main.tile[3];}
        
        return sprite;
    }
}
