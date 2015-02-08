package engine.silnik.item;

import engine.silnik.Vector2i;
import engine.silnik.main.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ItemSlot {

    private int x;
    private int y;
    private static Image item;
    private Item type;
    private boolean isFree = true;
    public int ammount;
    public boolean over = false;

    public ItemSlot(int xPos, int yPos) {
        x = xPos;
        y = yPos;

        //Default
        type = Item.FREE;
    }

    public void render(Graphics g, GameContainer gc) {
        if (!isFree && !type.equals(Item.FREE)) {
            item.draw(x, y);
            g.setColor(Color.white);
            g.drawString("" + ammount, x, y);

            if (Main.mouseOver(new Vector2i(x, y), item, gc.getInput())) {
                over = true;
            } else {
                over = false;
            }
        }
    }

    public void addItem(Item type, int ammount) {
        if (this.type.equals(Item.FREE)) {
            if (type.equals(Item.TRÆ001) && isFree) {
                item = Main.item[0];
                this.ammount += ammount;
                isFree = false;
            }
        }

        if (!isFree && !this.type.equals(Item.FREE)) {
            this.ammount += ammount;
        }

        this.type = type;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree_ONLY_FOR_DEBUG(boolean val) {
        isFree = val;
    }

    public boolean contains(Item type) {
        boolean re = false;

        if (type.equals(this.type)) {
            re = true;
        } else {
            re = false;
        }

        return re;
    }

    public void drawAN(int x, int y, Graphics g) {
        if (!isFree && !type.equals(Item.FREE)) {
            item.draw(x, y);
            g.setColor(Color.white);
            g.drawString("" + ammount, x, y);
        }
    }

    public Item getItem() {
        return type;
    }

    public int getAmmount() {
        return ammount;
    }

    public void reset() {
        type = Item.FREE;
        ammount = 0;
        isFree = true;
    }
}
