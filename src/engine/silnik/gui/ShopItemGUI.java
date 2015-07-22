package engine.silnik.gui;

import engine.silnik.Option;
import engine.silnik.Vector2i;
import engine.silnik.main.Game;
import engine.silnik.main.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class ShopItemGUI {

    private int x;
    private int y;
    private final Image LOGO;
    private final Image FRAME;
    private final Image BUTTON_NORMAL;
    private final Image BUTTON_HOVER;
    public String TITLE;
    public String ABOUT;
    private boolean hover = false;
    public int PRICE;
    public String priceText;
    private final Font font;
    private final Font priceFont;
    private final boolean mousePressed = false;
    private boolean buyed = false;
    private boolean buyable;
    public Shape itemBorder;
    public boolean insideBorder = true;
    private boolean onTime = false;

    public ShopItemGUI(int x, int y, Image logo, Image frame, Image button_n,
            Image button_h, String title, String about, int price, String priceMark) {

        this.x = x;
        this.y = y;
        this.LOGO = logo;
        this.FRAME = frame;
        this.BUTTON_NORMAL = button_h;
        this.BUTTON_HOVER = button_n;
        this.TITLE = title;
        this.ABOUT = about;
        this.PRICE = price;
        this.priceText = priceMark;

        font = new TrueTypeFont(new java.awt.Font("KenPixel High", java.awt.Font.BOLD, 14), true);
        priceFont = new TrueTypeFont(new java.awt.Font("KenPixel High", java.awt.Font.BOLD, 14), true);

        itemBorder = new Rectangle(x, y, frame.getWidth(), frame.getHeight());
    }

    public void render(int x, int y, Graphics g) {
        if (insideBorder) {
            FRAME.draw(x, y);
            LOGO.draw(x + 10, y + 9, 25, 22);
            font.drawString(x + 42, y + 5, TITLE, Color.orange);
            font.drawString(x + 42, y + 21, ABOUT, Color.green);

            if (hover) {
                BUTTON_NORMAL.draw(x + 237, y + 8);
            } else {
                BUTTON_HOVER.draw(x + 237, y + 8);
            }

            if (Game.coins >= PRICE && !buyed) {
                buyable = true;
                priceFont.drawString(x + 200, y + 13, PRICE + priceText, Color.green);
                hover = false;
            } else if (Game.coins < PRICE || buyed) {
                priceFont.drawString(x + 200, y + 13, PRICE + priceText, Color.red);
                buyable = false;
                hover = true;
            }
        }

        this.x = x;
        this.y = y;
        itemBorder.setX(x);
        itemBorder.setY(y);
    }

    public void update() {
        if (insideBorder) {
            if (Main.mouseOver(new Vector2i(x + 237, y + 8), BUTTON_NORMAL, Main.inputAll) && !buyed && buyable) {
                hover = true;
                if (Main.inputAll.isMousePressed(0)) {
                    buy();
                }
            } else {
                hover = false;
            }
        }
    }

    public void buy() {
        if (insideBorder) {
            if (Game.coins >= PRICE) {
                buyed = true;
                hover = true;
            }
        }
    }

    public void setPrice(int price) {
        this.PRICE = price;
    }

    public void setTitle(String title) {
        this.TITLE = title;
    }

    public void setAbout(String about) {
        this.ABOUT = about;
    }

    public boolean isMouseDownOnButton() {
        return mousePressed;
    }

    public boolean isBuyed() {
        if(buyed && !onTime) {
            onTime = true;
            return true;
        }else {
            return false;
        }
    }
}
