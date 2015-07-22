/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.silnik.gui;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author TKB
 */
public class ShopGUI {

    private int x;
    private int y;
    private int fixedY;
    private Image frame;
    public Shape frameBorder;
    public int scrollMovement;

    public ArrayList<ShopItemGUI> shopItems = new ArrayList<>();

    public ShopGUI(int x, int y, Image frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
        frameBorder = new Rectangle(x + 12, 220, 305, 160);
    }

    public void render(Graphics g) {
        frame.draw(x, y);
        for (int i = 0; i < shopItems.size(); i++) {
            shopItems.get(i).render(x + 14, y + 51 * i + 14 + scrollMovement, g);
        }
    }

    public void update() {
        shopItems.stream().map((shopItem) -> {
            shopItem.update();
            return shopItem;
        }).forEach((shopItem) -> {
            if (frameBorder.intersects(shopItem.itemBorder)) {
                shopItem.insideBorder = true;
            } else if (!frameBorder.contains(shopItem.itemBorder)) {
                shopItem.insideBorder = false;
            }
        });

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            scrollMovement -= 10;
        } else if (dWheel > 0) {
            scrollMovement += 10;
        }
    }

    public void addMenuItem(ShopItemGUI item) {
        System.out.println("ShopItem added: " + item.TITLE + ", " + item.ABOUT + ", " + item.PRICE + item.priceText);
        shopItems.add(item);
    }
    
    public boolean isItemBuyed(int item) {
        return shopItems.get(item).isBuyed();
    }
}
