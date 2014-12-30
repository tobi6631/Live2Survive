/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.silnik.tiles.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author TKB
 */
public class TileTypeSpriteASGN {
    public static Image asignSprite(SpriteSheet spriteSheet, TileType type) {
        Image sprite = null;
        
        if (type == TileType.JORD_TIL_GRÆS_VENSTREHJØRNE_TOP_0_0)  {sprite = spriteSheet.getSubImage(0, 0);}
        if (type == TileType.JORD_TIL_GRÆS_VENSTRESIDE_0_1)        {sprite = spriteSheet.getSubImage(0, 1);}
        if (type == TileType.JORD_TIL_GRÆS_VENSTREHJØRNE_DOWN_0_2) {sprite = spriteSheet.getSubImage(0, 2);}
        if (type == TileType.JORD_TIL_GRÆS_TOPDOWN_1_0)            {sprite = spriteSheet.getSubImage(1, 0);}
        if (type == TileType.GRÆS_1_1)                             {sprite = spriteSheet.getSubImage(1, 1);}
        if (type == TileType.JORD_TIL_GRÆS_DOWNTOP_1_2)            {sprite = spriteSheet.getSubImage(1, 2);}
        if (type == TileType.JORD_TIL_GRÆS_HØJREHJØRNE_TOP_2_0)    {sprite = spriteSheet.getSubImage(2, 0);}
        if (type == TileType.JORD_TIL_GRÆS_HØJRESIDE_2_1)          {sprite = spriteSheet.getSubImage(2, 1);}
        if (type == TileType.JORD_TIL_GRÆS_HØJREHJØRNE_DOWN_2_2)   {sprite = spriteSheet.getSubImage(2, 2);}
        
        return sprite;
    }
}
