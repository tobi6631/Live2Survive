package engine.silnik.item;

import game.tdev.Main.Main;
import game.tdev.main.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ItemSlot {

    private int x;
    private int y;
    private static Image item;
    private Item type;
    private boolean isFree = true;
    public int ammount;

    public ItemSlot(int xPos, int yPos) {
        x = xPos;
        y = yPos;

        //Default
        type = Item.FREE;
    }

    public void render(Graphics g) {
        if (!isFree && !type.equals(Item.FREE)) {
            item.draw(x, y);
            g.setColor(Color.white);
            g.drawString("" + ammount, x, y);
        }
    }

    public void addItem(Item type, int ammount) {
        if (this.type.equals(Item.FREE)) {
            this.type = type;

            if (type.equals(Item.TRÆ001) && isFree) {
                item = Main.item[0];
                this.ammount += ammount;
                isFree = false;
            }
        }

        if (!isFree) {
            this.ammount += ammount;
            isFree = false;
        }
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree_ONLY_FOR_DEBUG(boolean val) {
        isFree = val;
    }
    
    public boolean contains(Item type) {
        boolean re = false;
        
        if(type.equals(this.type)) {
            re = true;
        }else {
            re = false;
        }
        
        return re;
    }
}
